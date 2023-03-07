package com.dnd_8th_4_android.wery.presentation.ui.group.create.viewmodel

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
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
    var groupImgHttpUrl = MutableLiveData<String>("")

    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> = _groupId

    private val _groupImg: MutableLiveData<Uri> = MutableLiveData("".toUri())
    val groupImg: LiveData<Uri> = _groupImg

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _groupInformationData: MutableLiveData<ResponseGroupInformationData> =
        MutableLiveData()
    val groupInformationData: LiveData<ResponseGroupInformationData> = _groupInformationData

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

    fun patchModifyGroup(data: HashMap<String, RequestBody>, image: MultipartBody.Part?) {
        val groupIdRequestBody = _groupId.value.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        data["groupId"] = groupIdRequestBody
        if (image == null) {
            viewModelScope.launch {
                groupRepository.modifyGroup(data, null).onSuccess {
                    _isLoading.value = false
                }.onFailure { _isLoading.value = false }
            }
        } else {
            viewModelScope.launch {
                groupRepository.modifyGroup(data, image).onSuccess {
                    _isLoading.value = false
                }.onFailure { _isLoading.value = false }
            }
        }
    }

    fun getGroupInformation(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getGroupInformation(groupId)
            }.onSuccess {
                groupNameTxt.value = it.data.groupName
                groupIntroduceTxt.value = it.data.groupNote
                groupImgHttpUrl.value = it.data.groupImageUrl
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

    fun setGroupId(idValue: Int) {
        _groupId.value = idValue
    }

    fun setImageUri(image: Uri) {
        _groupImg.value = image
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}