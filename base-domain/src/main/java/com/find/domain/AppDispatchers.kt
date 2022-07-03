package com.find.domain

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}
