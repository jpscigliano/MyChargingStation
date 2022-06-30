package com.find.presentation.mapUI.viewModel

import com.find.domain.model.ID

data class MapViewState(
    val isLoading: Boolean = false,
    val listOfPOI: List<Marker> = listOf(),
    val showRefreshButton: Boolean = false,
    val userLocationLatitude: Double? = null,
    val userLocationLongitude: Double? = null
) {

    data class Marker(
        val id: ID,
        val title: String,
        val latitude: Double,
        val longitude: Double,
        val description: String
    )
}
