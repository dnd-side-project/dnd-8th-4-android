package com.dnd_8th_4_android.wery.presentation.ui.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateGroupViewModel : ViewModel() {

    var groupNameTxt = MutableLiveData<String>()
    var groupIntroduceTxt = MutableLiveData<String>()
}