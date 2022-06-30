package com.find.framework.di

import com.find.data.datasource.LocalChargingStationDataSource
import com.find.data.datasource.LocationDataSource
import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.domain.mapper.Mapper
import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.framework.local.ChargerStationsDataSourceImpl
import com.find.framework.local.UserLocationDataSourceImpl
import com.find.framework.local.entity.ChargingStationEntry
import com.find.framework.local.entity.LocationEntry
import com.find.framework.local.mapper.ChargingStationEntryToModelMapper
import com.find.framework.local.mapper.ChargingStationToEntryMapper
import com.find.framework.local.mapper.CoordinatesToEntryMapper
import com.find.framework.local.mapper.UserLocationEntryToModelMapper
import com.find.framework.remote.NetworkPOIDataSource
import com.find.framework.remote.dto.PoiResponseDto
import com.find.framework.remote.mapper.POIResponseDtoToModelMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworkModule {

    @Binds
    abstract fun binRemotePOIDataSource(networkPOIDataSource: NetworkPOIDataSource): RemoteChargingStationDataSource

    @Binds
    abstract fun bindLocalPOIDataSource(dataBasePOIDataSource: ChargerStationsDataSourceImpl): LocalChargingStationDataSource


    @Binds
    abstract fun bindPOIResponseDtoToModelMapper(mapper: POIResponseDtoToModelMapper): Mapper<PoiResponseDto, ChargingStation>

    @Binds
    abstract fun bindChargingStationToModelMapper(mapper: ChargingStationEntryToModelMapper): Mapper<ChargingStationEntry, ChargingStation>

    @Binds
    abstract fun bindChargingStationToEntryMapper(mapper: ChargingStationToEntryMapper): Mapper<ChargingStation, ChargingStationEntry>

    @Binds
    abstract fun bindLocationDataSource(locationDataSourceImpl: UserLocationDataSourceImpl): LocationDataSource

    @Binds
    abstract fun bindCoordinatesToLocationEntryMapper(mapper: CoordinatesToEntryMapper): Mapper<Coordinates, LocationEntry>

    @Binds
    abstract fun bindLocationEntryToCoordinatesMapper(mapper: UserLocationEntryToModelMapper): Mapper<LocationEntry, Coordinates>

}