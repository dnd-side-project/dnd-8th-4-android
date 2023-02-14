package com.dnd_8th_4_android.wery.domain.model

import com.dnd_8th_4_android.wery.R

enum class PopupWindowType(val emotionPosition: Int, val drawable: Int, val emotionName: String, ) {
    Type1(1, R.drawable.img_crying_face, "슬픔"),
    Type2(2, R.drawable.img_no_group, "기쁨"),
    Type3(3, R.drawable.img_popup_window, "놀람"),
    Type4(4, R.drawable.img_checkbox_checked, "당황"),
    Type5(5, R.drawable.img_checkbox_default, "짜증"),
    Type6(6, R.drawable.ic_all_my_group, "메롱")
}