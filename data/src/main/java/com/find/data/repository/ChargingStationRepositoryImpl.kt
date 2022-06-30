package com.find.data.repository

import com.find.data.datasource.LocalChargingStationDataSource
import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.domain.model.Distance
import com.find.domain.model.ID
import com.find.domain.repository.ChargingStationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


internal class ChargingStationRepositoryImpl @Inject constructor(
    private val localDataSource: LocalChargingStationDataSource,
    private val remoteDataSource: RemoteChargingStationDataSource
) : ChargingStationRepository {
    override suspend fun updatePOIClosedToCoordinates(
        coordinates: Coordinates,
        distanceFromCoordinate: Distance
    ) {
        val fetchedData = remoteDataSource.getPoiList(coordinates, distanceFromCoordinate)
        localDataSource.saveListOfChargingStations(fetchedData)
    }

    override suspend fun observeChargingStations(): Flow<List<ChargingStation>> =
        localDataSource.observeListOfChargingStations()


    override suspend fun observerPOI(id: ID): Flow<ChargingStation> =
        localDataSource.observeChargingStation(id)


}