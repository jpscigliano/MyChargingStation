package com.find.framework.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfoResponseDto(
    @SerialName("ID") val id: Int,
    @SerialName("description") val description: String?,
    @SerialName("AddressLine1") val addressLine: String?,
    @SerialName("Town") val town: String?,
    @SerialName("StateOrProvince") val state: String?,
    @SerialName("Postcode") val postCode: String?,
    @SerialName("Country") val countryResponseDto: CountryResponseDto?,
    @SerialName("Latitude") val latitude: Double?,
    @SerialName("Longitude") val longitude: Double?
)


