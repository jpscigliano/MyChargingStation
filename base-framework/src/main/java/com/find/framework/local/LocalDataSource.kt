package com.find.framework.local


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object LocalDataSource {
    fun <MODEL_ROOM, MODEL> observeFromLocal(
        call: () -> Flow<MODEL_ROOM>,
        entryToModelMapper: (MODEL_ROOM) -> MODEL,
    ): Flow<MODEL> {
        return call()
            .map {
                entryToModelMapper(it)
            }
            .flowOn(Dispatchers.Default)
    }

    suspend fun <MODEL_ROOM> insertToLocal(
        modelToEntryMapper: () -> MODEL_ROOM,
        call: suspend (MODEL_ROOM) -> Unit
    ) {
        withContext(Dispatchers.Default) {
            call(modelToEntryMapper())
        }
    }
}