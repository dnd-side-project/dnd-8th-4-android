package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileChangeViewModel : ViewModel() {

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _textCount = MutableLiveData(0)
    val textCount: LiveData<Int> = _textCount

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _textCount.value = p0?.length
            _isEnabled.value = p0?.length!! in 0..14
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    fun setTextCount(value: Int) {
        _textCount.value = value
    }
}