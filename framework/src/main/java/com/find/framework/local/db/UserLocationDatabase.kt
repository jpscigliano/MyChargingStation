package com.find.framework.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.find.framework.local.dao.UserLocationDao
import com.find.framework.local.entity.LocationEntry

internal const val DB_NAME_USER_LOCATION = "USER_LOCATION_DB"

@Database(
    entities = [
        LocationEntry::class],
    version = 1,
    exportSchema = false
)
internal abstract class UserLocationDatabase : RoomDatabase() {
    abstract fun userLocationDao(): UserLocationDao
}

