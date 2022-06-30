package com.find.presentation.mapUI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.find.presentation.*
import com.find.presentation.databinding.FragmentMapBinding
import com.find.presentation.mapUI.viewModel.MapViewModel
import com.find.presentation.mapUI.viewModel.MapViewState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMapBinding
        get() = FragmentMapBinding::inflate

    val viewModel: MapViewModel by viewModels()

    var clusterManager: ClusterManager<MarkerInfo>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.icReload.setOnClickListener {
            viewModel.syncChargingStations()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        clusterManager = ClusterManager<MarkerInfo>(context, googleMap)

        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        clusterManager?.setOnClusterItemInfoWindowClickListener {
            viewModel.onMarkerClicked(it.id)
        }
        viewModel.viewState.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED)
        { mapViewState ->
            binding.loading.visible(mapViewState.isLoading)
            binding.icReload.isVisible = mapViewState.showRefreshButton
            setupMarkerInMap(mapViewState.listOfPOI, clusterManager)
            googleMap.setMapTargetIfPossible(
                mapViewState.userLocationLatitude,
                mapViewState.userLocationLongitude
            )
        }

        viewModel.event.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED)
        { event ->
            when (event) {
                is Event.NavigateTo ->
                    findNavController().navigate(event.destination)
                is Event.ShowMessage ->
                    makeText(
                        requireContext(),
                        "Error: ${event.textState.getStringText(requireContext())}",
                        LENGTH_LONG
                    ).show()
                is Event.Success ->
                    makeText(
                        requireContext(),
                        "Update completed successfully",
                        LENGTH_LONG
                    ).show()

            }
        }

    }

    private fun setupMarkerInMap(
        markers: List<MapViewState.Marker>,
        clusterManager: ClusterManager<MarkerInfo>?
    ): List<MarkerInfo> {
        val mappedMarkers = markers.mapNotNull {
            MarkerInfo(
                id = it.id,
                titleText = it.title,
                descriptionText = it.description,
                latitude = it.latitude,
                longitude = it.longitude,

                )
        }
        clusterManager?.addItems(mappedMarkers)
        clusterManager?.cluster()
        return mappedMarkers


    }

    override fun onDestroyView() {
        clusterManager?.clearItems()
        clusterManager?.cluster()
        clusterManager = null
        super.onDestroyView()
    }

    private fun GoogleMap.setMapTargetIfPossible(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(LatLng(latitude, longitude))
                        .zoom(13f).build()
                )
            )
        }
    }

    private fun ContentLoadingProgressBar.visible(loading: Boolean) {
        if (loading) {
            binding.loading.show()
        } else {
            binding.loading.hide()
        }
    }
}