package com.example.eldercare.util.context

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.hideKeyboard(view: View) {
    val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun Context.stringOf(
    @StringRes resId: Int,
) = getString(resId)

fun Context.colorOf(
    @ColorRes resId: Int,
) = ContextCompat.getColor(this, resId)