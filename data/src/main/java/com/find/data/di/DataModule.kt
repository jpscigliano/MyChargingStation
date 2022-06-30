package com.find.data.di

import com.find.domain.repository.ChargingStationRepository
import com.find.domain.repository.LocationRepository
import com.find.data.repository.ChargingStationRepositoryImpl
import com.find.data.repository.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun bindChargingStationRepository(repositoryImpl: ChargingStationRepositoryImpl): ChargingStationRepository

    @Binds
    @Singleton
    internal abstract fun bindLocationRepository(repositoryImpl: LocationRepositoryImpl): LocationRepository

}