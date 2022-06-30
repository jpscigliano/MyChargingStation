package com.find.presentation

import androidx.navigation.NavDirections

sealed class Event {
    data class ShowMessage(val textState: TextState) : Event()
    data class NavigateTo(val destination: NavDirections) : Event()
    object Success : Event()
}

