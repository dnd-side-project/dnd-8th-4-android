package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileChangeViewModel @Inject constructor(private val myPageRepository: MyPageRepository) :
    ViewModel() {

    private val _groupImg: MutableLiveData<Uri> = MutableLiveData("".toUri())
    val groupImg: LiveData<Uri> = _groupImg

    private val _httpUri = MutableLiveData<String>()
    val httpUri: LiveData<String> = _httpUri

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _textCount = MutableLiveData(0)
    val textCount: LiveData<Int> = _textCount

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _textCount.value = p0?.length
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    fun setEnabled() {
        _isEnabled.value = true
    }

    fun setTextCount(value: Int) {
        _textCount.value = value
    }

    fun setImageUri(image: Uri) {
        _groupImg.value = image
    }

    fun setHttpUrl(imageUrl: String) {
        _httpUri.value = imageUrl
    }

    fun setNickNameRequestBodyData(nickName: String): HashMap<String, RequestBody> {
        val nickNameRequestBody = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        val textHashMap = HashMap<String, RequestBody>()
        textHashMap["nickName"] = nickNameRequestBody

        return textHashMap
    }

    fun patchModifyGroup(data: HashMap<String, RequestBody>, image: MultipartBody.Part?) {
        if (image == null) {
            viewModelScope.launch {
                kotlin.runCatching {
                    myPageRepository.modifyProfile(data, null)
                }.onSuccess {
                    if (it.code == 0) {
                        _isSuccess.value = true
                    }
                }.onFailure {
                    Timber.tag("error").d(it.message.toString())
                }
            }
        } else {
            viewModelScope.launch {
                kotlin.runCatching {
                    myPageRepository.modifyProfile(data, image)
                }.onSuccess {
                    if (it.code == 0) {
                        _isSuccess.value = true
                    }
                }.onFailure {
                    Timber.tag("error").d(it.message.toString())
                }
            }
        }
    }
}