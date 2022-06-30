package com.find.domain.usecase.observers


import com.find.domain.interactor.ObserverInteractor
import com.find.domain.model.Coordinates
import com.find.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserLocation @Inject constructor(
    private val locationRepository: LocationRepository
) : ObserverInteractor<Unit, Coordinates>() {

    override suspend fun execute(params: Unit): Flow<Coordinates> {
        return locationRepository.observerUserLocation()
    }

}

