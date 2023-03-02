package com.dnd_8th_4_android.wery.presentation.ui.post.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionFeed
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponsePostData
import com.dnd_8th_4_android.wery.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) :
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

    private val _postResultData = MutableLiveData<ResponsePostData>()
    var postResultData: LiveData<ResponsePostData> = _postResultData

    private val _photoUrlList = MutableLiveData<List<ResponsePostData.ResultPost.Collect>>()
    var photoUrlList: LiveData<List<ResponsePostData.ResultPost.Collect>> = _photoUrlList

    private val _missionStickerData = MutableLiveData<ResponseMissionFeed>()
    var missionStickerData: LiveData<ResponseMissionFeed> = _missionStickerData

    fun setPhotoCnt(cntValue: Int) {
        _photoCnt.value = cntValue
    }

    fun setGroupId(idValue: Long) {
        _groupId.value = idValue
    }

    fun getExistingPostData(contentId: Int) {
        viewModelScope.launch {
            postRepository.getPostData(contentId).onSuccess {
                _postResultData.value = it
                setExistingPostData(it)
            }
        }
    }

    private fun setExistingPostData(data: ResponsePostData) {
        selectedGroup.value = data.data.groupName
        selectedLatitude.value = data.data.latitude.toString()
        selectedLongitude.value = data.data.longitude.toString()
        selectedPlace.value = data.data.location ?: "어디를 방문하셨나요?"
        noteTxt.value = data.data.content
        _photoUrlList.value = data.data.collect
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
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun uploadMissionFeed(
        missionId: Int,
        data: HashMap<String, RequestBody>,
        images: MutableList<MultipartBody.Part>?
    ) {
        data["missionId"] = missionId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                postRepository.uploadMissionFeed(data, images)
            }.onSuccess {
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun modifyFeed(
        data: HashMap<String, RequestBody>,
        multipartFile: MutableList<MultipartBody.Part>
    ) {
        viewModelScope.launch {
            postRepository.modifyFeed(data, multipartFile).onSuccess {
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun setUploadRequestBodyData(
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

    fun setModifyRequestBodyData(
        content: String,
        contentId: String,
        latitude: String,
        longitude: String,
        location: String
    ): HashMap<String, RequestBody> {
        val contentRequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
        val contentIdRequestBody = contentId.toRequestBody("text/plain".toMediaTypeOrNull())
        val latitudeRequestBody = latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longitudeRequestBody = longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val locationRequestBody = location.toRequestBody("text/plain".toMediaTypeOrNull())

        val textHashMap = HashMap<String, RequestBody>()
        textHashMap["content"] = contentRequestBody
        textHashMap["contentId"] = contentIdRequestBody
        if (location != "어디를 방문하셨나요?") {
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