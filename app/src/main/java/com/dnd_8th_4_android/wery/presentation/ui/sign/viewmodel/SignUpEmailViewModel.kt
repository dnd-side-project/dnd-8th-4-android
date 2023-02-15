package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpEmailViewModel : ViewModel() {

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _isEnabled.value = p0?.length!! > 0
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }
}