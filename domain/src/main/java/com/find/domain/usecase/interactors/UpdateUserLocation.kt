package com.find.domain.usecase.interactors


import com.find.domain.interactor.Interactor
import com.find.domain.model.Coordinates
import com.find.domain.model.Latitude
import com.find.domain.model.Longitude
import com.find.domain.repository.LocationRepository
import javax.inject.Inject


class UpdateUserLocation @Inject constructor(
    private val locationRepository: LocationRepository
) : Interactor<Unit>() {
    override suspend fun execute(params: Unit) {

        locationRepository.saveUserLocation(
            Coordinates(
                Latitude(52.526),
                Longitude(13.415)
            )
        )

    }
}