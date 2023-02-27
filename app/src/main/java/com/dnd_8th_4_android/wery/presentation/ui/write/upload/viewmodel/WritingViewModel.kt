package com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(private val postRepository: PostRepository) :
    ViewModel() {

    private var _photoCnt = MutableLiveData<Int>(0)
    val photoCnt: LiveData<Int> = _photoCnt

    var noteTxt = MutableLiveData<String>()
    var selectedPlace = MutableLiveData<String>()
    var selectedLatitude = MutableLiveData<String>("-1.0")
    var selectedLongitude = MutableLiveData<String>("-1.0")
    var selectedGroup = MutableLiveData<String>()
    var selectedGroupState = MutableLiveData<Boolean>(false)

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _groupId: MutableLiveData<Long> = MutableLiveData()
    val groupId: LiveData<Long> = _groupId

    private val _groupListData = MutableLiveData<List<ResponseGroupList.ResultGroupList>>()
    var groupListData: LiveData<List<ResponseGroupList.ResultGroupList>> = _groupListData

    private val _postResultData = MutableLiveData<BaseResponse>()
    var postResultData: LiveData<BaseResponse> = _postResultData

    fun setPhotoCnt(cntValue: Int) {
        _photoCnt.value = cntValue
    }

    fun setGroupId(idValue: Long) {
        _groupId.value = idValue
    }

    fun getGroupList() {
        viewModelScope.launch {
            postRepository.getMyGroupList().onSuccess {
                _groupListData.value = it.data
            }
        }
    }

    fun uploadFeed(
        groupId: Long,
        data: HashMap<String, RequestBody>,
        multipartFile: MutableList<MultipartBody.Part>
    ) {
        viewModelScope.launch {
            postRepository.uploadFeed(groupId, data, multipartFile).onSuccess {
                _postResultData.value = it
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun setRequestBodyData(
        content: String,
        latitude: String,
        longitude: String,
        location: String
    ): HashMap<String, RequestBody> {
        val contentRequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val latitudeRequestBody = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longitudeRequestBody = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val locationRequestBody = location.toRequestBody("text/plain".toMediaTypeOrNull())

        val textHashMap = HashMap<String, RequestBody>()
        textHashMap["content"] = contentRequestBody
        if (latitude != "-1.0") {
            textHashMap["latitude"] = latitudeRequestBody
            textHashMap["longitude"] = longitudeRequestBody
            textHashMap["location"] = locationRequestBody
        }
        return textHashMap
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

}