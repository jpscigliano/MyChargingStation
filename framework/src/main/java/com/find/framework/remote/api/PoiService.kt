package com.find.framework.remote.api

import com.find.framework.remote.dto.PoiResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PoiService {
    @GET("/poi")
    suspend fun getPoiList(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("distance") distance: Int,
        @Query("distanceUnit") distanceUnit: String,
        @Query("verbose")smallResponse:Boolean=true,
        @Query("compact")compactResponse:Boolean=false
    ): Response<List<PoiResponseDto>>

}