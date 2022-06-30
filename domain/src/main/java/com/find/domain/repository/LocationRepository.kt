package com.find.domain.repository


import com.find.domain.model.Coordinates
import kotlinx.coroutines.flow.Flow


interface LocationRepository {

    fun observerUserLocation(): Flow<Coordinates>

    suspend fun saveUserLocation(coordinates: Coordinates)

    suspend fun getLatestUserLocation(): Coordinates
}