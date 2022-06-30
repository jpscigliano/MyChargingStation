package com.find.framework.remote

import com.find.data.datasource.RemoteChargingStationDataSource
import com.find.domain.mapper.Mapper
import com.find.domain.mapper.toList
import com.find.domain.model.ChargingStation
import com.find.domain.model.Coordinates
import com.find.domain.model.Distance
import com.find.framework.remote.api.PoiService
import com.find.framework.remote.dto.PoiResponseDto
import javax.inject.Inject


internal class NetworkPOIDataSource @Inject constructor(
    private val poiApi: PoiService,
    private val poiResponseToModelMapper: Mapper<PoiResponseDto, ChargingStation>
) : RemoteChargingStationDataSource {

    override suspend fun getPoiList(
        coordinates: Coordinates,
        distanceFromCoordinate: Distance
    ): List<ChargingStation> {
        return RemoteDataSource.getRemoteResult(
            mapModelToRequestDto = { },
            call = {
                poiApi.getPoiList(
                    latitude = coordinates.latitude.value.toString(),
                    longitude = coordinates.longitude.value.toString(),
                    distance = distanceFromCoordinate.value,
                    distanceUnit = "KM"
                )
            },
            mapResponseDtoToModel = { poiResponseToModelMapper.toList().map(it) }
        )
    }

}