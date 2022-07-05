package com.find.data

import app.cash.turbine.test
import com.find.data.datasource.LocalChargingStationDataSource
import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.data.repository.ChargingStationRepositoryImpl
import com.find.domain.mocks.SampleData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
class ChargingStationRepositoryTest {


    private val remoteDataSource: RemoteChargingStationDataSource = mockk()
    private val localDataSource: LocalChargingStationDataSource = mockk()

    private val repository = ChargingStationRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    @Before
    fun setup() {
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


    }


    @Test
    fun `WHEN ObserveChargingStations is executed THEN a list of ChargingStation is c ollected`() =
        runTest {
            repository.observeChargingStations().test {
                assertEquals(awaitItem(), SampleData.provideListOfChargingStation())
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN an  ID WHEN  observePOI  is called from the repository THEN a charging station is collected`() =
        runTest {
            repository.observerPOI(SampleData.provideChargingStationID()).test {
                assertEquals(awaitItem(), SampleData.provideChargingStationForID())
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN coordinates AND a Distance in KM WHEN  UpdatePOIClosedToCoordinates interactor is called THEN  the local datasource saves the item once`() =
        runTest {
            repository.updatePOIClosedToCoordinates(
                SampleData.provideCoordinates(),
                SampleData.provideNumericDistanceInKm().distanceFromCoordinates
            )
            coVerify(exactly = 1) {
                localDataSource.saveListOfChargingStations(any())
            }
        }
}