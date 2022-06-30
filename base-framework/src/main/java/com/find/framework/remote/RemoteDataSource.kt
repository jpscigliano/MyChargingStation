package com.find.framework.remote

import com.find.domain.AppException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object RemoteDataSource {

    /**
     * Get RETROFIT response
     *
     * @param mapModelToRequestDto prepares the model to be sent in the request - if nothing is supposed to be sent then write 'preTransform = {}'
     * @param call the retrofit api call - returning a [Response]
     * @param mapResponseDtoToModel transforms from the api response model to the domain module
     */
    suspend fun <REQUEST_API_MODEL, RESPONSE_API_MODEL, MODEL> getRemoteResult(
        mapModelToRequestDto: (suspend () -> REQUEST_API_MODEL),
        call: suspend (REQUEST_API_MODEL) -> Response<RESPONSE_API_MODEL>,
        mapResponseDtoToModel: (suspend (RESPONSE_API_MODEL) -> MODEL),
    ): MODEL {

        // verify internet
        if (withContext(Dispatchers.IO) { !NetworkingUtils.hasInternetConnection() })
            throw AppException.NoInternetError

        try {
            // call api
            val response = call(mapModelToRequestDto())
            return when {
                response.isSuccessful && response.body() != null ->
                    mapResponseDtoToModel(response.body()!!)

                else ->
                    throw ErrorParser.parseException(response.code(), response.errorBody())
            }
        } catch (e: Exception) {
            throw AppException.UnknownError(e.message ?: "Unknown error")
        }
    }
}

