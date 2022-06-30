package com.find.framework.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PoiResponseDto(
    @SerialName("ID") val id: Long,
    @SerialName("AddressInfo") val addressInfo: AddressInfoResponseDto?,
    @SerialName("NumberOfPoints") val numberOfPoint: Int?,
    @SerialName("OperatorInfo") val operatorInfo: OperatorInfoResponseDto?,
    @SerialName("UsageType") val usageType: UsageTypeResponseDto?,

    )
