@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.domain

import com.find.domain.mocks.SampleData
import com.find.domain.model.ChargingStation
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.usecase.observers.ObserveChargingStations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ObserveChargingStationsTest {

    private val chargingRepository = mockk<ChargingStationRepository>()

    private val useCase = ObserveChargingStations(
        repository = chargingRepository,
    )

    @BeforeTest
    fun setup() {
        coEvery { chargingRepository.observeChargingStations() } returns flow { emit(SampleData.provideListOfChargingStation()) }

    }

    @Test
    fun WHEN_ObserveChargingStationsInteractorIsObserve_THEN_ListChargingStationsIsCollected() =
        runTest {
            val chargingStationList: List<ChargingStation> = useCase(Unit).first()
            assertEquals(chargingStationList, SampleData.provideListOfChargingStation())
        }


}