package com.find.domain.usecase.observers

import com.find.domain.interactor.ObserverInteractor
import com.find.domain.model.ChargingStation
import com.find.domain.repository.ChargingStationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveChargingStations @Inject constructor(
    private val repository: ChargingStationRepository
) : ObserverInteractor<Unit, List<@JvmSuppressWildcards ChargingStation>>() {

    override suspend fun execute(params: Unit): Flow<List<ChargingStation>> {
        return repository.observeChargingStations()
    }
}



