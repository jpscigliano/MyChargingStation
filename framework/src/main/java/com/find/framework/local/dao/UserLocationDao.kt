package com.find.framework.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.find.framework.local.EntryDao
import com.find.framework.local.entity.LocationEntry
import kotlinx.coroutines.flow.Flow


@Dao
abstract class UserLocationDao : EntryDao<LocationEntry>() {

    @Query("SELECT  * FROM locationEntry")
    abstract fun observeUserLocation(): Flow<LocationEntry>

    @Query("DELETE  FROM locationEntry")
    abstract fun deleteAll()

    @Query("SELECT  * FROM locationEntry")
    abstract fun getUserLocation(): LocationEntry

}