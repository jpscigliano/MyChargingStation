package com.find.data.datasource

import com.find.domain.model.Coordinates
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    fun observeUserLocation(): Flow<Coordinates>
    suspend fun saveLocationCoordinates(location: Coordinates)
    suspend fun deleteLocationCoordinates()
    suspend fun getUserLocation(): Coordinates?
}