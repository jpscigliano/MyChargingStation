package com.find.domain.interactor


import com.find.domain.AppException
import com.find.domain.ExecutionInteractorStatus
import com.find.domain.ExecutionInteractorStatus.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

abstract class Interactor<T> {
    operator fun invoke(
        params: T,
        executionTimeout: Long = TIME_OUT
    ): Flow<ExecutionInteractorStatus> = flow {
        try {
            withTimeout(executionTimeout) {
                emit(ExecutionInProgress)
                execute(params)
                emit(ExecutionSuccess)
            }
        } catch (t: TimeoutCancellationException) {
            emit(ExecutionError(AppException.TimeOut))
        }
    }.catch {
        emit(ExecutionError(it))
    }

    protected abstract suspend fun execute(params: T)

    companion object {
        val TIME_OUT = TimeUnit.MINUTES.toMillis(5)
    }
}