package com.find.framework.di

import android.content.Context
import androidx.room.Room
import com.find.framework.local.dao.ChargingStationDao
import com.find.framework.local.dao.UserLocationDao
import com.find.framework.local.db.ChargingStationsDatabase
import com.find.framework.local.db.DB_NAME_CHARGION_STATION
import com.find.framework.local.db.DB_NAME_USER_LOCATION
import com.find.framework.local.db.UserLocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideChargerStationDataBase(@ApplicationContext context: Context): ChargingStationsDatabase =
        Room.databaseBuilder(
            context,
            ChargingStationsDatabase::class.java,
            DB_NAME_CHARGION_STATION
        ).build()

    @Provides
    @Singleton
    internal fun provideUserLocationDataBase(@ApplicationContext context: Context): UserLocationDatabase =
        Room.databaseBuilder(
            context,
            UserLocationDatabase::class.java,
            DB_NAME_USER_LOCATION
        ).build()

    @Provides
    internal fun provideChargingStationsDao(database: ChargingStationsDatabase): ChargingStationDao {
        return database.chargingStationDao()
    }

    @Provides
    internal fun provideUserLocationDao(database: UserLocationDatabase): UserLocationDao {
        return database.userLocationDao()
    }
}