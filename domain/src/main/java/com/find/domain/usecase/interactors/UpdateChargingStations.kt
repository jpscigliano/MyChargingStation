package com.find.domain.usecase.interactors

import com.find.domain.interactor.Interactor
import com.find.domain.model.Distance
import com.find.domain.model.DistanceUnit
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateChargingStations @Inject constructor(
    private val chargingStationsRepository: ChargingStationRepository,
    private val locationRepository: LocationRepository
) : Interactor<UpdateChargingStations.Params>() {
    override suspend fun execute(params: Params) {

        locationRepository.getLatestUserLocation().let { coordinates ->
            withContext(Dispatchers.IO) {
                chargingStationsRepository.updatePOIClosedToCoordinates(
                    coordinates,
                    params.distanceFromCoordinates
                )
            }
        }
    }

    data class Params(
        // val coordinates: Coordinates,
        val distanceFromCoordinates: Distance,
        val distanceUnit: DistanceUnit
    )
}

