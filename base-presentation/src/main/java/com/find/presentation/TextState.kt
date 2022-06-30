package com.find.presentation

import android.content.Context
import androidx.annotation.StringRes

sealed class TextState {
    data class StringText(val value: String) : TextState()
    data class ResourceText(@StringRes val value: Int) : TextState()
}

fun TextState.getStringText(context: Context): String = when (this) {
    is TextState.ResourceText -> context.getString(this.value)
    is TextState.StringText -> this.value

}