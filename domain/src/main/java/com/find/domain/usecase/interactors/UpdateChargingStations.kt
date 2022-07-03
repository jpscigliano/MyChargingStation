package com.find.domain.usecase.interactors

import com.find.domain.AppDispatchers
import com.find.domain.interactor.Interactor
import com.find.domain.model.Distance
import com.find.domain.model.DistanceUnit
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.repository.LocationRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateChargingStations @Inject constructor(
    private val chargingStationsRepository: ChargingStationRepository,
    private val locationRepository: LocationRepository,
    private val appDispatchers: AppDispatchers
) : Interactor<UpdateChargingStations.Params>() {
    override suspend fun execute(params: Params) {
        withContext(appDispatchers.io) {
            locationRepository.getLatestUserLocation()?.let {
                chargingStationsRepository.updatePOIClosedToCoordinates(
                    coordinates = it,
                    distanceFromCoordinate = params.distanceFromCoordinates
                )
            }
        }
    }

    data class Params(
        val distanceFromCoordinates: Distance,
        val distanceUnit: DistanceUnit
    )
}

