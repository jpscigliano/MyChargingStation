package com.find.data.datasource

import com.find.domain.model.ChargingStation
import com.find.domain.model.ID
import kotlinx.coroutines.flow.Flow

interface LocalChargingStationDataSource {
    suspend fun observeListOfChargingStations(): Flow<List<ChargingStation>>
    suspend fun observeChargingStation(id: ID): Flow<ChargingStation>
    suspend fun saveListOfChargingStations(poiList: List<ChargingStation>)
    suspend fun deleteAll()
}
