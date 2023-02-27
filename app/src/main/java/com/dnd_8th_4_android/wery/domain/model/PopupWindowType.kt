package com.dnd_8th_4_android.wery.domain.model

import com.dnd_8th_4_android.wery.R

enum class PopupWindowType(val emotionPosition: Int, val drawable: Int, val emotionName: String, ) {
    Type1(0, R.drawable.img_best, "최고예요"),
    Type2(1, R.drawable.img_fun, "웃겨요"),
    Type3(2, R.drawable.img_good, "좋아요"),
    Type4(3, R.drawable.img_wonder, "궁금해요"),
    Type5(4, R.drawable.img_sad, "속상해요"),
    Type6(5, R.drawable.img_angry, "화나요")
}