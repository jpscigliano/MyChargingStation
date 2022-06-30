package com.find.domain.model

data class Address(
    val addressLine: AddressLine,
    val town: Town,
    val state: State,
    val postCode: PostCode,
    val coordinates: Coordinates
) {
}