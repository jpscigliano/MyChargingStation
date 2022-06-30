package com.find.domain.di

import com.find.domain.interactor.Interactor
import com.find.domain.interactor.ObserverInteractor
import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.domain.model.ID
import com.find.domain.usecase.interactors.UpdateChargingStations
import com.find.domain.usecase.interactors.UpdateUserLocation
import com.find.domain.usecase.observers.ObserveChargingStationDetail
import com.find.domain.usecase.observers.ObserveChargingStations
import com.find.domain.usecase.observers.ObserveUserLocation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


typealias UpdateChargingStationsInteractor = Interactor<UpdateChargingStations.Params>
typealias UpdateUserLocationInteractor = Interactor<Unit>
typealias ObserveChargingStationsInteractor = ObserverInteractor<Unit, List<@JvmSuppressWildcards ChargingStation>>
typealias ObserveChargingStationDetailInteractor = ObserverInteractor<ID, ChargingStation>
typealias  ObserveUserLocationInteractor = ObserverInteractor<Unit, Coordinates>

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindSyncPOIClosedToCoordinates(interactor: UpdateChargingStations): UpdateChargingStationsInteractor

    @Binds
    @Singleton
    fun bindObserverUserLocationInteractor(observer: ObserveUserLocation): ObserveUserLocationInteractor

    @Binds
    @Singleton
    fun bindUpdateUserLocationInteractor(interactor: UpdateUserLocation): UpdateUserLocationInteractor


    @Binds
    @Singleton
    fun bindObserverChargingStationsInteractor(observer: ObserveChargingStations): ObserveChargingStationsInteractor


    @Binds
    @Singleton
    fun bindObserverChargingStationDetailInteractor(observer: ObserveChargingStationDetail): ObserveChargingStationDetailInteractor
}