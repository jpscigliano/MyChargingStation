package com.find.domain


sealed class AppException(msg: String) : Exception(msg) {
    object NoInternetError : AppException("Internet not available")
    object TimeOut : AppException("This is taking too long!")
    object LocationNotAvailable:AppException("Location not available")
    data class UnknownError(val msg: String = "UnknownError") : AppException(msg)
    sealed class ApiError(msg: String) : AppException(msg) {
        object InvalidRequest : ApiError("Invalid request")
        object NotAuthorize : ApiError("Not authorize")
        object NotFound : ApiError("Not found")
        object InvalidPOI : ApiError("Invalid POI")
    }
}

fun Throwable.asAppExceptionOrUnknown(): AppException {
    return if (this is AppException) this
    else AppException.UnknownError()
}