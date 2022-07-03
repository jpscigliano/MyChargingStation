package com.find.presentation.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text:String){
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_LONG
    ).show()

}fun Context.showToast(text:Int){
    Toast.makeText(
        this,
        text,
        Toast.LENGTH_LONG
    ).show()
}