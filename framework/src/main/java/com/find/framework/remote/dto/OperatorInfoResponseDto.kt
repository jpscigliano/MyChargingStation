package com.find.framework.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OperatorInfoResponseDto(
    @SerialName("ID") val id: Int,
    @SerialName("Title") val title: String?,
    @SerialName("WebsiteURL") val website: String?,
    )