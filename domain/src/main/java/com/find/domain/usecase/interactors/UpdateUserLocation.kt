package com.find.domain.usecase.interactors


import com.find.domain.AppDispatchers
import com.find.domain.interactor.Interactor
import com.find.domain.model.Coordinates
import com.find.domain.model.Latitude
import com.find.domain.model.Longitude
import com.find.domain.repository.LocationRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateUserLocation @Inject constructor(
    private val locationRepository: LocationRepository,
    private val appDispatchers: AppDispatchers
) : Interactor<Unit>() {
    override suspend fun execute(params: Unit) = withContext(appDispatchers.io) {
        locationRepository.saveUserLocation(
            coordinates = Coordinates(Latitude(52.526), Longitude(13.415))
        )
    }
}