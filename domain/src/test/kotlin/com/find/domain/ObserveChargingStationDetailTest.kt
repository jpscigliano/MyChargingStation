@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.domain

import app.cash.turbine.test
import com.find.domain.mocks.SampleData
import com.find.domain.model.ChargingStation
import com.find.domain.model.ID
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.usecase.observers.ObserveChargingStationDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ObserveChargingStationDetailTest {

    private val chargingRepository = mockk<ChargingStationRepository>()

    private val useCase = ObserveChargingStationDetail(repository = chargingRepository)

    @BeforeTest
    fun setup() {
        coEvery { chargingRepository.observerPOI(SampleData.provideChargingStationID()) } returns flow {
            emit(
                SampleData.provideChargingStationForID()
            )
        }
    }

    @Test
    fun GIVEN_ChargingStationID_WHEN_ObserveChargingStationDetailInteractorIsObserve_THEN_ChargingStationsIsCollected() =
        runTest {
            val chargingStation: ChargingStation =
                useCase(SampleData.provideChargingStationID()).first()
            assertEquals(chargingStation, SampleData.provideChargingStationForID())
        }

    @Test
    fun GIVEN_InvalidChargingStationID_WHEN_ObserveChargingStationDetailInteractorIsObserve_THEN_AssertionErrorIsThrow() =
        runTest {
            useCase(ID(-1)).test { awaitError() }

        }


}