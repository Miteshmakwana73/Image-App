package com.imageapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * My Extensions
 */



/**
 * toast
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * toast with duration
 */
fun Context.toast(message: String, duration: Int) {
    Toast.makeText(this, message, duration).show()
}

/**
 * snack bar
 */
fun View.showSnackBarToast(strMessage: String) {
    try {
        Snackbar.make(this, strMessage, Snackbar.LENGTH_LONG).show()
    } catch (e: Exception) {
        // exception
    }
}

/**
 * make view visible
 */
fun View.makeVisible() {
    visibility = View.VISIBLE
}

/**
 * make view gone
 */
fun View.makeGone() {
    visibility = View.GONE
}

/**
 * make view invisible
 */
fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

/**
 * make view click disable
 */
fun View.disable() {
    alpha = .3f
    isClickable = false
}

/**
 * make view click enable
 */
fun View.enable() {
    alpha = 1f
    isClickable = true
}

/**
 * make view disable
 */
// with no alpha
fun View.isDisable() {
    isClickable = false
    isEnabled = false
}

/**
 * make view enable
 */
fun View.isEnable() {
    isClickable = true
    isEnabled = true
}

/**
 * hide keyboard
 */
fun Activity.hideKeyboard() {
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val focusedView = this.currentFocus
    if (focusedView != null) {
        inputManager.hideSoftInputFromWindow(
            focusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

/**
 * change date format
 */
fun String.dateFormat(currentfromate: String, newfromate: String): String {
    val sdf = SimpleDateFormat(currentfromate, Locale.ENGLISH)

    try {
        return SimpleDateFormat(newfromate).format(sdf.parse(this))

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""

}

/**
 * change date format
 */
fun Date.dateToStringFormat(format: String): String {
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)

    try {
        return sdf.format(this)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ""

}