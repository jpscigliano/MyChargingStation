package com.find.framework.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.find.framework.local.EntryDao
import com.find.framework.local.entity.ChargingStationEntry
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ChargingStationDao : EntryDao<ChargingStationEntry>() {

    @Query("SELECT * FROM chargingStationEntry")
    abstract fun observeChargingStations(): Flow<List<ChargingStationEntry>>

    @Query("SELECT * FROM chargingStationEntry WHERE id=:id")
    abstract fun observeChargingStation(id: Long): Flow<ChargingStationEntry>

    @Query("DELETE  FROM chargingstationentry")
    abstract fun deleteAll()

}