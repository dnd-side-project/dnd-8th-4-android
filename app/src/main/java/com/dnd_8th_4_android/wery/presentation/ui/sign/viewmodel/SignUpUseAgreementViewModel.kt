package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpUseAgreementViewModel : ViewModel() {
    private val _isEnabled = MutableLiveData(false)
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _isSelectedBox = MutableLiveData(0)
    val isSelectedBox: LiveData<Int> = _isSelectedBox

    fun onClickCheckBox(v: View) {
        val checkBox = v as CheckBox

        if (checkBox.isChecked) {
            _isSelectedBox.value = _isSelectedBox.value?.inc()

            if (_isSelectedBox.value == 3) {
                _isEnabled.value = true
            }
        } else {
            _isSelectedBox.value = _isSelectedBox.value?.dec()
            _isEnabled.value = false
        }
    }

    fun setSelectedBox(value: Int) {
        _isSelectedBox.value = value
    }

    fun setEnabled() {
        _isEnabled.value = true
    }

    fun setUnEnabled() {
        _isEnabled.value = false
    }
}