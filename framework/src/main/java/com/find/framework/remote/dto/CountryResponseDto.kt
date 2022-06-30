package com.find.framework.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponseDto(
    @SerialName("ID") val id: Int,
    @SerialName("ISOCode") val isoCode: String?,
    @SerialName("ContinentCode") val continentCode: String?,
    @SerialName("Description") val description: String?
)