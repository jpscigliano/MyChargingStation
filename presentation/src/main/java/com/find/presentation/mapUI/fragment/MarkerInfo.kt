package com.find.presentation.mapUI.fragment

import com.find.domain.model.ID
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MarkerInfo(
    val id: ID,
    val titleText: String,
    val descriptionText: String,
    val latitude: Double,
    val longitude: Double,

    ) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)

    override fun getTitle(): String? = titleText

    override fun getSnippet(): String? = descriptionText


}