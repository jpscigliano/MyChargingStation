package com.find.data.datasource

import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.domain.model.Distance

interface RemoteChargingStationDataSource {
    suspend fun getPoiList(
        coordinates: Coordinates,
        distanceFromCoordinate: Distance
    ): List<ChargingStation>
}