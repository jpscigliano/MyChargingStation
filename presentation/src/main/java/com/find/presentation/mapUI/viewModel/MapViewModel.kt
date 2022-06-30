package com.find.presentation.mapUI.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.find.presentation.TextState
import com.find.presentation.detailUI.fragment.DetailNavArg
import com.find.presentation.mapUI.fragment.MapFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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
        viewModelScope.launch { syncPOI() }
        viewModelScope.launch(Dispatchers.IO) { updateUserLocation(Unit).collect() }
    }

    val viewState: StateFlow<MapViewState> = combine(
        updatePoiLoadingState.flow,
        observeChargingStations(Unit),
        observeUserLocationInteractor(Unit),
        _Event
    ) { loading, poiList, userLocation, event ->
        println("APP - Loading :$loading  User :${userLocation}  Event :$event")
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
                        println("APP - SUCCESS")
                        _Event.emit(Event.Success)
                    },
                    onError = {
                        println("APP - ERROR")
                        _Event.emit(Event.ShowMessage(TextState.StringText(it.message ?: "")))

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