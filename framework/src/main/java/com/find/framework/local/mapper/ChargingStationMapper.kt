package com.find.framework.local.mapper

import com.find.domain.mapper.Mapper
import com.find.domain.model.*
import com.find.framework.local.entity.ChargingStationEntry
import javax.inject.Inject


class ChargingStationEntryToModelMapper @Inject constructor() :
    Mapper<ChargingStationEntry, ChargingStation> {
    override fun map(input: ChargingStationEntry): ChargingStation {
        return ChargingStation(
            id = ID(input.id),
            operator = Operator(input.operator),
            usageType = UsageType(input.usageType),
            amountOfChargingPoints = ChargingPoints(input.chargingPoints),
            address = Address(
                addressLine = AddressLine(input.address),
                town = Town(input.town),
                state = State(input.state),
                postCode = PostCode(input.postCode),
                coordinates = Coordinates(
                    Latitude(input.latitude),
                    Longitude(input.longitude)
                )
            )

        )
    }
}

class ChargingStationToEntryMapper @Inject constructor() :
    Mapper<ChargingStation, ChargingStationEntry> {
    override fun map(input: ChargingStation): ChargingStationEntry {
        return ChargingStationEntry(
            id = input.id.value,
            operator = input.operator.value,
            usageType = input.usageType.value,
            chargingPoints = input.amountOfChargingPoints.value,
            town = input.address.town.value,
            state = input.address.state.value,
            postCode = input.address.postCode.value,
            address = input.address.addressLine.value,
            latitude = input.address.coordinates.latitude.value,
            longitude = input.address.coordinates.longitude.value

        )
    }
}