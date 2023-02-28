package com.dnd_8th_4_android.wery.presentation.ui.group.create.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    var groupNameTxt = MutableLiveData<String>()
    var groupIntroduceTxt = MutableLiveData<String>()

    private val _groupImg: MutableLiveData<Uri> = MutableLiveData("".toUri())
    val groupImg: LiveData<Uri> = _groupImg

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun postCreateGroup(data: HashMap<String, RequestBody>, image: MultipartBody.Part?) {
        if (image == null) {
            viewModelScope.launch {
                groupRepository.createGroup(data, null).onSuccess {
                    _isLoading.value = false
                }.onFailure { _isLoading.value = false }
            }
        } else {
            viewModelScope.launch {
                groupRepository.createGroup(data, image).onSuccess {
                    _isLoading.value = false
                }.onFailure { _isLoading.value = false }
            }
        }
    }

    fun setGroupRequestBodyData(
        groupName: String,
        groupNote: String?
    ): HashMap<String, RequestBody> {
        val groupNameRequestBody = groupName.toRequestBody("text/plain".toMediaTypeOrNull())
        val groupNoteRequestBody = groupNote?.toRequestBody("text/plain".toMediaTypeOrNull())

        val textHashMap = HashMap<String, RequestBody>()
        textHashMap["groupName"] = groupNameRequestBody
        textHashMap["groupNote"] = groupNoteRequestBody!!

        return textHashMap
    }

    fun setImageUri(image: Uri) {
        _groupImg.value = image
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}