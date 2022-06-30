package com.find.framework.remote.mapper

import com.find.domain.mapper.Mapper
import com.find.domain.model.*
import com.find.framework.remote.dto.PoiResponseDto
import javax.inject.Inject


class POIResponseDtoToModelMapper @Inject constructor() : Mapper<PoiResponseDto, ChargingStation> {
    override fun map(input: PoiResponseDto): ChargingStation {
        return ChargingStation(
            id = ID(input.id),
            operator = Operator(input.operatorInfo?.title ?: ""),
            usageType = UsageType(input.usageType?.title ?: ""),
            amountOfChargingPoints = ChargingPoints(input.numberOfPoint ?: 0),
            address = Address(
                addressLine = AddressLine(input.addressInfo?.addressLine ?: ""),
                town = Town(input.addressInfo?.town ?: ""),
                state = State(input.addressInfo?.state ?: ""),
                postCode = PostCode(input.addressInfo?.postCode ?: ""),
                coordinates = Coordinates(
                    Latitude(input.addressInfo?.latitude ?: 0.0),
                    Longitude(input.addressInfo?.longitude ?: 0.0)
                )
            )

        )
    }
}