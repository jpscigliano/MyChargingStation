@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.domain

import com.find.domain.ExecutionInteractorStatus.*
import com.find.domain.mocks.SampleData
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.repository.LocationRepository
import com.find.domain.usecase.interactors.UpdateChargingStations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class UpdateChargingStationsTest {


    private val chargingRepository = mockk<ChargingStationRepository>()
    private val locationRepository = mockk<LocationRepository>()

    private val useCase = UpdateChargingStations(
        chargingStationsRepository = chargingRepository,
        locationRepository = locationRepository,
        appDispatchers = SampleData.provideTestDispatcher()
    )

    @BeforeTest
    fun setup() {
        coEvery { chargingRepository.updatePOIClosedToCoordinates(any(), any()) } returns Unit
    }

    @Test
    fun GIVEN_AnyDistanceInKm_AND_coordinatesAreEmitted_WHEN_UpdatingCharginStationInteractorIsCalled_THEN_InProgressAndSuccessAreEmitted()=
        runTest {
            coEvery { locationRepository.getLatestUserLocation() } returns SampleData.provideCoordinates()
            val useCaseExecutionStates: List<ExecutionInteractorStatus> = useCase(SampleData.provideNumericDistanceInKm()).toList()
            assertEquals(useCaseExecutionStates, listOf(ExecutionInProgress, ExecutionSuccess))
        }



}