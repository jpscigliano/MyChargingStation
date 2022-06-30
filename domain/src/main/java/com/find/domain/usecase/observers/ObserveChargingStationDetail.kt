package com.find.domain.usecase.observers

import com.find.domain.interactor.ObserverInteractor
import com.find.domain.model.ChargingStation
import com.find.domain.model.ID
import com.find.domain.repository.ChargingStationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveChargingStationDetail @Inject constructor(
    private val repository: ChargingStationRepository
) : ObserverInteractor<ID, ChargingStation>() {
    override suspend fun execute(params: ID): Flow<ChargingStation> {
        return repository.observerPOI(id = params)
    }
}



