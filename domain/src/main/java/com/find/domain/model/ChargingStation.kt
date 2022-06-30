package com.find.domain.model

data class ChargingStation(
    val id: ID,
    val operator: Operator,
    val address: Address,
    val usageType: UsageType,
    val amountOfChargingPoints: ChargingPoints
)
