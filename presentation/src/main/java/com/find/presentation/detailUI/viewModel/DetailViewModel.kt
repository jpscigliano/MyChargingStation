package com.find.presentation.detailUI.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.find.domain.di.ObserveChargingStationDetailInteractor
import com.find.domain.model.ID
import com.find.presentation.detailUI.fragment.DetailNavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    observeChargingStationDetail: ObserveChargingStationDetailInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    val viewState: StateFlow<DetailViewState> = observeChargingStationDetail(
        ID(savedStateHandle.get<DetailNavArg>("detailNavArgs")!!.id)
    ).map {
        DetailViewState(
            isLoading = false,
            id = "${it.id.value}",
            title = it.operator.value,
            address = "${it.address.addressLine.value} - ${it.address.town.value} - ${it.address.state.value} - ${it.address.postCode.value}",
            amountCharges = "${it.amountOfChargingPoints.value}"

        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailViewState(isLoading = true)
    )

}