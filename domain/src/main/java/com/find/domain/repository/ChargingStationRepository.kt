package com.find.domain.repository


import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.domain.model.Distance
import com.find.domain.model.ID
import kotlinx.coroutines.flow.Flow


interface ChargingStationRepository {

    suspend fun updatePOIClosedToCoordinates(coordinates: Coordinates, distanceFromCoordinate: Distance)

    suspend fun observeChargingStations(): Flow<List<ChargingStation>>

    suspend fun observerPOI(id: ID): Flow<ChargingStation>

}