@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.presentation


import app.cash.turbine.test
import com.find.data.datasource.LocalChargingStationDataSource
import com.find.data.datasource.LocationDataSource
import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.data.repository.ChargingStationRepositoryImpl
import com.find.data.repository.LocationRepositoryImpl
import com.find.domain.ExecutionInteractorStatus
import com.find.domain.model.Distance
import com.find.domain.model.DistanceUnit
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.repository.LocationRepository
import com.find.domain.usecase.interactors.UpdateChargingStations
import com.find.domain.usecase.interactors.UpdateUserLocation
import com.find.domain.usecase.observers.ObserveChargingStations
import com.find.domain.usecase.observers.ObserveUserLocation
import com.find.presentation.mocks.SampleData
import com.find.presentation.mocks.SampleData.provideTestDispatcher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


class IntegrationTests {

    //Datasource
    private val localDataSource: LocalChargingStationDataSource = mockk()
    private val remoteDataSource: RemoteChargingStationDataSource = mockk()
    private val locationDataSource: LocationDataSource = mockk()

    //Repositories
    private val chargingRepo: ChargingStationRepository =
        ChargingStationRepositoryImpl(localDataSource, remoteDataSource)
    private val locationRepo: LocationRepository = LocationRepositoryImpl(locationDataSource)

    //Interactors
    private val updateChargingStations =
        UpdateChargingStations(chargingRepo, locationRepo, provideTestDispatcher())
    private val updateUserLocation = UpdateUserLocation(locationRepo, provideTestDispatcher())
    private val observeUserLocation = ObserveUserLocation(locationRepo)
    private val observeChargingStations = ObserveChargingStations(chargingRepo)


    @BeforeTest
    fun setUp() {
        coEvery {
            remoteDataSource.getPoiList(any(), any())
        } returns SampleData.provideListOfChargingStation()

        coEvery {
            localDataSource.observeListOfChargingStations()
        } returns flow {
            emit(SampleData.provideListOfChargingStation())
        }
        coEvery {
            localDataSource.observeChargingStation(any())
        } returns flow {
            emit(SampleData.provideChargingStationForID())
        }
        coEvery {
            localDataSource.saveListOfChargingStations(any())
        } returns Unit

        coEvery {
            locationDataSource.getUserLocation()
        } returns SampleData.provideCoordinates()

        coEvery {
            locationDataSource.deleteLocationCoordinates()
        } returns Unit
        coEvery {
            locationDataSource.saveLocationCoordinates(any())
        } returns Unit
    }


    @Test
    fun `WHEN updateChargingStation is called  THEN InProgress and Success should be emitted`() =
        runTest {
            updateChargingStations(
                UpdateChargingStations.Params(
                    Distance(5),
                    DistanceUnit.KM
                )
            ).test {
                assertEquals(awaitItem(), ExecutionInteractorStatus.ExecutionInProgress)
                assertEquals(awaitItem(), ExecutionInteractorStatus.ExecutionSuccess)
                awaitComplete()
            }
        }

    @Test
    fun `WHEN updateChargingStation  is called THEN  LocalChargingDataSource should save new Stations `() =
        runTest {
            updateChargingStations(
                UpdateChargingStations.Params(
                    Distance(5),
                    DistanceUnit.KM
                )
            ).collect()
            coVerify(exactly = 1) { localDataSource.saveListOfChargingStations(any()) }

        }

    @Test
    fun `WHEN updateUserLocation is called THEN InProgress and Success should be emitted `() =
        runTest {
            updateUserLocation(Unit).test {
                assertEquals(awaitItem(), ExecutionInteractorStatus.ExecutionInProgress)
                assertEquals(awaitItem(), ExecutionInteractorStatus.ExecutionSuccess)
                awaitComplete()
            }
        }

    @Test
    fun `WHEN updateUserLocation is called THEN  LocationDataSource should delete previous location and store new `() =
        runTest {
            updateUserLocation(Unit).collect()
            coVerify(exactly = 1) { locationDataSource.deleteLocationCoordinates() }
            coVerify(exactly = 1) { locationDataSource.saveLocationCoordinates(any()) }
        }


    @Test
    fun `WHEN observing for chargingStations is called THEN  DataSource should observe for local stations`() =
        runTest {
            observeChargingStations(Unit).collect()
            coVerify(exactly = 1) { localDataSource.observeListOfChargingStations() }
        }


}