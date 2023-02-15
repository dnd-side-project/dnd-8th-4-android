package com.dnd_8th_4_android.wery.presentation.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class ViewExt(private val mContext: Context) {

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(view: View) {
        view.requestFocus()
        val inputMethodManager =
            mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}