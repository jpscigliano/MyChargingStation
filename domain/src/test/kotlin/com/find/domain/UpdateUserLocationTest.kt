@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.domain

import com.find.domain.ExecutionInteractorStatus.*
import com.find.domain.mocks.SampleData
import com.find.domain.repository.LocationRepository
import com.find.domain.usecase.interactors.UpdateUserLocation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class UpdateUserLocationTest {

    private val locationRepository = mockk<LocationRepository>()

    private val useCase = UpdateUserLocation(
        locationRepository = locationRepository,
        appDispatchers = SampleData.provideTestDispatcher()
    )


    @Test
    fun `GIVEN a Coordinate WHEN UpdateUserLocation is executed THEN InProgress and Success are emitted`() =
        runTest {
            coEvery { locationRepository.saveUserLocation(any()) } returns Unit
            val useCaseExecutionStates: List<ExecutionInteractorStatus> = useCase(Unit).toList()
            assertEquals(useCaseExecutionStates, listOf(ExecutionInProgress, ExecutionSuccess))
        }

    @Test
    fun `GIVEN a Coordinate WHEN UpdateUserLocation is executed AND SaveUserLocation emits exception THEN InProgress and then Error are emitted`() =
        runTest {
            coEvery { locationRepository.saveUserLocation(any()) } throws AppException.UnknownError()
            val useCaseExecutionStates: List<ExecutionInteractorStatus> = useCase(Unit).toList()
            assertEquals(
                useCaseExecutionStates,
                listOf(ExecutionInProgress, ExecutionError(AppException.UnknownError()))
            )
        }


}