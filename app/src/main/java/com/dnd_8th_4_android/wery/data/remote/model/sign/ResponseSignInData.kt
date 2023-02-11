package com.dnd_8th_4_android.wery.data.remote.model.sign

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseSignInData(
    val jwt: String
) : BaseResponse()