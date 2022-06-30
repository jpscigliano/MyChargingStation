package com.find.framework.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.find.framework.local.dao.ChargingStationDao
import com.find.framework.local.entity.ChargingStationEntry

internal const val DB_NAME_CHARGION_STATION = "CHARGING_STATIONS_DB"

@Database(
    entities = [
        ChargingStationEntry::class],
    version = 1,
    exportSchema = false
)
internal abstract class ChargingStationsDatabase : RoomDatabase() {
    abstract fun chargingStationDao(): ChargingStationDao
}

