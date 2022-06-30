package com.find.framework.local

import com.find.data.datasource.LocationDataSource
import com.find.domain.mapper.Mapper
import com.find.domain.model.Coordinates
import com.find.framework.local.dao.UserLocationDao
import com.find.framework.local.entity.LocationEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class UserLocationDataSourceImpl @Inject constructor(
    private val userLocationDao: UserLocationDao,
    private val locationEntryToModelMapper: Mapper<LocationEntry, Coordinates>,
    private val coordinatesToEntryMapper: Mapper<Coordinates, LocationEntry>
) : LocationDataSource {

    override fun observeUserLocation(): Flow<Coordinates> {
        return LocalDataSource.observeFromLocal(
            call = { userLocationDao.observeUserLocation().filterNotNull() },
            entryToModelMapper = { locationEntryToModelMapper.map(it) }
        )
    }

    override suspend fun saveLocationCoordinates(location: Coordinates) {

        LocalDataSource.insertToLocal(
            modelToEntryMapper = { coordinatesToEntryMapper.map(location) },
            call = { userLocationDao.insert(it) }
        )
    }

    override suspend fun deleteLocationCoordinates() {
        userLocationDao.deleteAll()
    }

    override suspend fun getUserLocation(): Coordinates {
        return LocalDataSource.getFromLocal(
            call = { userLocationDao.getUserLocation() },
            entryToModelMapper = { locationEntryToModelMapper.map(it) })
    }


}