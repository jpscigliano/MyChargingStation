package com.find.framework.local

import com.find.data.datasource.LocalChargingStationDataSource
import com.find.domain.mapper.Mapper
import com.find.domain.mapper.toList
import com.find.domain.model.ChargingStation
import com.find.domain.model.ID
import com.find.framework.local.dao.ChargingStationDao
import com.find.framework.local.entity.ChargingStationEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject


class ChargerStationsDataSourceImpl @Inject constructor(
    private val chargerStationDao: ChargingStationDao,
    private val stationEntryToModelMapper: Mapper<ChargingStationEntry, ChargingStation>,
    private val stationModelToEntryMapper: Mapper<ChargingStation, ChargingStationEntry>
) : LocalChargingStationDataSource {
    override suspend fun observeListOfChargingStations(): Flow<List<ChargingStation>> {
        return LocalDataSource.observeFromLocal(
            call = { chargerStationDao.observeChargingStations() },
            entryToModelMapper = { stationEntryToModelMapper.toList().map(it) }
        )
    }

    override suspend fun observeChargingStation(id: ID): Flow<ChargingStation> {
        return LocalDataSource.observeFromLocal(
            call = { chargerStationDao.observeChargingStation(id.value).filterNotNull() },
            entryToModelMapper = { stationEntryToModelMapper.map(it) }
        )
    }

    override suspend fun saveListOfChargingStations(poiList: List<ChargingStation>) {
        LocalDataSource.insertToLocal(
            modelToEntryMapper = { stationModelToEntryMapper.toList().map(poiList) },
            call = { chargerStationDao.insertAll(it) },
        )
    }

    override suspend fun deleteAll() {
        chargerStationDao.deleteAll()
    }

}