package com.find.domain.interactor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class ObserverInteractor<PARAMS, T> {

    private val paramState = MutableSharedFlow<PARAMS>(replay = 1)

    val flow: Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest { params ->
            execute(params)
        }
        .distinctUntilChanged()


    operator fun invoke(params: PARAMS): Flow<T> {
        paramState.tryEmit(params)
        return flow
    }

    protected abstract suspend fun execute(params: PARAMS): Flow<T>
}