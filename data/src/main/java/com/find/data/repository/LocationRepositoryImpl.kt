package com.find.data.repository

import com.find.data.datasource.LocationDataSource
import com.find.domain.model.Coordinates
import com.find.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationRepository {
    override fun observerUserLocation(): Flow<Coordinates> =
        locationDataSource.observeUserLocation()

    override suspend fun saveUserLocation(coordinates: Coordinates) {
        locationDataSource.deleteLocationCoordinates()
        locationDataSource.saveLocationCoordinates(coordinates)
    }

    override suspend fun getLatestUserLocation(): Coordinates? =
        locationDataSource.getUserLocation()

}