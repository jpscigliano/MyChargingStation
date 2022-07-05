package com.find.presentation.mapUI.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.find.domain.AppException
import com.find.domain.LoadingState
import com.find.domain.collectExecutionStatus
import com.find.domain.di.ObserveChargingStationsInteractor
import com.find.domain.di.ObserveUserLocationInteractor
import com.find.domain.di.UpdateChargingStationsInteractor
import com.find.domain.di.UpdateUserLocationInteractor
import com.find.domain.model.Distance
import com.find.domain.model.DistanceUnit
import com.find.domain.model.ID
import com.find.domain.usecase.interactors.UpdateChargingStations
import com.find.presentation.Event
import com.find.presentation.R
import com.find.presentation.TextState
import com.find.presentation.detailUI.fragment.DetailNavArg
import com.find.presentation.mapUI.fragment.MapFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val updateChargingStations: UpdateChargingStationsInteractor,
    private val updateUserLocation: UpdateUserLocationInteractor,
    private val observeChargingStations: ObserveChargingStationsInteractor,
    private val observeUserLocationInteractor: ObserveUserLocationInteractor,
) : ViewModel() {

    private val updatePoiLoadingState = LoadingState()

    private val _Event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _Event

    init {
        viewModelScope.launch { updateUserLocation(Unit).collect() }
        viewModelScope.launch { syncPOI() }

    }

    val viewState: StateFlow<MapViewState> = combine(
        updatePoiLoadingState.flow,
        observeChargingStations(Unit),
        observeUserLocationInteractor(Unit),
        _Event
    ) { loading, poiList, userLocation, event ->
        MapViewState(
            isLoading = loading,
            showRefreshButton = event !is Event.Success && loading.not(),
            listOfPOI = poiList.map {
                MapViewState.Marker(
                    id = it.id,
                    title = "ID:${it.id.value} ${it.operator.value}",
                    latitude = it.address.coordinates.latitude.value,
                    longitude = it.address.coordinates.longitude.value,
                    description = it.usageType.value
                )
            },
            userLocationLatitude = userLocation.latitude.value,
            userLocationLongitude = userLocation.longitude.value,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MapViewState(isLoading = true)
    )

    fun onMarkerClicked(id: ID) {
        viewModelScope.launch {
            _Event.emit(Event.NavigateTo(MapFragmentDirections.actionToDetail(DetailNavArg(id.value))))
        }
    }

    fun syncChargingStations() {
        viewModelScope.launch {
            syncPOI()
        }

    }

    var syncJob: Job? = null
    private suspend fun syncPOI() {
        delay(50)
        syncJob = viewModelScope.launch {
            while (true) {
                updateChargingStations(
                    UpdateChargingStations.Params(
                        Distance(5),
                        DistanceUnit.KM
                    )
                ).collectExecutionStatus(
                    loader = updatePoiLoadingState,
                    onSuccess = {
                        _Event.emit(Event.Success)
                    },
                    onError = {
                        val message = when (it) {
                            is AppException.NoInternetError -> R.string.error_msg_no_connectivity
                            is AppException.LocationNotAvailable -> R.string.error_msg_location_not_available
                            is AppException.TimeOut -> R.string.error_msg_taking_to_long
                            is AppException.ApiError.InvalidRequest -> R.string.error_msg_invalid_request
                            is AppException.ApiError.NotAuthorize -> R.string.error_msg_not_authorize
                            else -> R.string.error_msg_unknown

                        }
                        _Event.emit(Event.ShowMessage(TextState.ResourceText(message)))

                    }
                )
                delay(TimeUnit.SECONDS.toMillis(30))
            }
        }

    }

    override fun onCleared() {
        syncJob?.cancel(CancellationException())
        super.onCleared()
    }
}