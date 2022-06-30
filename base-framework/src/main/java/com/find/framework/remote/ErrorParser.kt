package com.find.framework.remote


import com.find.domain.AppException
import okhttp3.ResponseBody


object ErrorParser {
    private fun parseException(responseErrorCode: Int): AppException {
        return when (responseErrorCode) {
            400 -> AppException.ApiError.InvalidRequest
            403 -> AppException.ApiError.InvalidRequest
            404 -> AppException.ApiError.NotAuthorize
            1001 -> AppException.ApiError.InvalidPOI // Custom error codes could be added like this.
            else -> AppException.UnknownError()
        }
    }

    fun parseException(code: Int, errorBody: ResponseBody?): AppException {
        return parseException(code)
    }
}
