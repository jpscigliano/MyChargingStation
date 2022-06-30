package com.find.presentation.detailUI.viewModel

data class DetailViewState(
    val isLoading: Boolean = false,
    val id: String = "",
    val title: String = "",
    val address: String = "",
    val amountCharges: String = ""
)