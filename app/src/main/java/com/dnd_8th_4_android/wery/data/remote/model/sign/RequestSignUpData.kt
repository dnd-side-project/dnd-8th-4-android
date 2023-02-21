package com.dnd_8th_4_android.wery.data.remote.model.sign

data class RequestSignUpData(
    val name: String,
    val email: String,
    val password: String,
    val nickName: String,
)