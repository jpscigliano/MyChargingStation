@file:OptIn(ExperimentalCoroutinesApi::class)

package com.find.presentation.mocks

import com.find.domain.AppDispatchers
import com.find.domain.model.*
import com.find.domain.usecase.interactors.UpdateChargingStations
import com.find.presentation.detailUI.viewModel.DetailViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

object SampleData {

    fun provideTestDispatcher(): AppDispatchers = object : AppDispatchers {
        override val default: CoroutineDispatcher = UnconfinedTestDispatcher()
        override val io: CoroutineDispatcher = UnconfinedTestDispatcher()
        override val main: CoroutineDispatcher = UnconfinedTestDispatcher()
    }

    fun provideCoordinates(): Coordinates = Coordinates(Latitude(15.5), Longitude(16.0))

    fun provideNumericDistanceInKm() =
        UpdateChargingStations.Params(Distance(5), DistanceUnit.KM)

    fun provideListOfChargingStation(): List<ChargingStation> =
        listOf(
            ChargingStation(
                id = ID(101),
                operator = Operator("SHL provider"),
                address = Address(
                    addressLine = AddressLine("Far away 21"),
                    town = Town("Berlin"),
                    state = State("Brandenburg"),
                    postCode = PostCode("10247"),
                    coordinates = Coordinates(Latitude(12.7), Longitude(35.5))
                ),
                usageType = UsageType("Open to Public"),
                amountOfChargingPoints = ChargingPoints(5)
            ),
            ChargingStation(
                id = ID(156),
                operator = Operator("XWZ provider"),
                address = Address(
                    addressLine = AddressLine("Next to Home 19"),
                    town = Town("Berlin"),
                    state = State("Brandenburg"),
                    postCode = PostCode("10247"),
                    coordinates = Coordinates(Latitude(12.6), Longitude(35.8))
                ),
                usageType = UsageType("Open to Public"),
                amountOfChargingPoints = ChargingPoints(1)
            ),
        )


    fun provideChargingStationID(): ID = ID(101)

    fun provideChargingStationForID(): ChargingStation = ChargingStation(
        id = ID(101),
        operator = Operator("SHL provider"),
        address = Address(
            addressLine = AddressLine("Far away 21"),
            town = Town("Berlin"),
            state = State("Brandenburg"),
            postCode = PostCode("10247"),
            coordinates = Coordinates(Latitude(12.7), Longitude(35.5))
        ),
        usageType = UsageType("Open to Public"),
        amountOfChargingPoints = ChargingPoints(5)
    )

    fun provideChargingStationViewStateForID() = DetailViewState(
        isLoading = false,
        id = "101",
        title = "SHL provider",
        address = "Far away 21 - Berlin - Brandenburg - 10247",
        amountCharges = "5"
    )


}