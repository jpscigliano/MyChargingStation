package com.find.framework.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChargingStationEntry(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "operator_name") val operator: String,
    @ColumnInfo(name = "usage_type") val usageType: String,
    @ColumnInfo(name = "amount_charging_points") val chargingPoints: Int,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "town") val town: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "post_code") val postCode: String,
    @ColumnInfo(name = "address_line") val address: String,


    )
