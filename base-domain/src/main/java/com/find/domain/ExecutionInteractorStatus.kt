package com.find.domain

import com.find.domain.ExecutionInteractorStatus.*
import kotlinx.coroutines.flow.Flow

sealed class ExecutionInteractorStatus {
    object ExecutionInProgress : ExecutionInteractorStatus()
    object ExecutionSuccess : ExecutionInteractorStatus()
    data class ExecutionError(val exception: Throwable) : ExecutionInteractorStatus()
}


suspend fun Flow<ExecutionInteractorStatus>.collectExecutionStatus(
    loader: LoadingState = LoadingState(),
    onError: suspend (AppException) -> Unit = {},
    onSuccess: suspend () -> Unit = { }
) = collect { status ->
    when (status) {
        is ExecutionInProgress ->
            loader.startLoading()

        is ExecutionSuccess -> {
            loader.stopLoading()
            onSuccess.invoke()
        }
        is ExecutionError -> {
            loader.stopLoading()
            onError.invoke(status.exception.asAppExceptionOrUnknown())

        }
    }
}
