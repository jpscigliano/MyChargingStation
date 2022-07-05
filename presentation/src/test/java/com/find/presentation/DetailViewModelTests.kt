@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.presentation


import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.find.data.datasource.LocalChargingStationDataSource
import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.data.repository.ChargingStationRepositoryImpl
import com.find.domain.repository.ChargingStationRepository
import com.find.domain.usecase.observers.ObserveChargingStationDetail
import com.find.presentation.detailUI.fragment.DetailNavArg
import com.find.presentation.detailUI.viewModel.DetailViewModel
import com.find.presentation.mocks.SampleData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


class DetailViewModelTests {

    //Datasource
    private val localDataSource: LocalChargingStationDataSource = mockk()
    private val remoteDataSource: RemoteChargingStationDataSource = mockk()

    //Repositories
    private val chargingRepo: ChargingStationRepository =
        ChargingStationRepositoryImpl(localDataSource, remoteDataSource)

    //Interactor
    val observeChargingStationDetail = ObserveChargingStationDetail(chargingRepo)
    private val savedStateHandle: SavedStateHandle = mockk()

    private lateinit var detailViewModel: DetailViewModel


    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every {
            savedStateHandle.get<DetailNavArg>("detailNavArgs")
        } returns DetailNavArg(101)

        coEvery {
            localDataSource.observeChargingStation(any())
        } returns flow {
            emit(SampleData.provideChargingStationForID())
        }
        detailViewModel = DetailViewModel(
            observeChargingStationDetail,
            savedStateHandle
        )

    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }


    @Test
    fun `WHEN updateChargingStation is called  THEN InProgress and Success should be emitted`() =
        runTest {
            detailViewModel.viewState.test {
                assertEquals(awaitItem(), SampleData.provideChargingStationViewStateForID())
            }
        }
}