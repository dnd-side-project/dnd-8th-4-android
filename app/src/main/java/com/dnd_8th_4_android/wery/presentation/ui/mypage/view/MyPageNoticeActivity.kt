package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageNoticeBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageNoticeActivity :
    BaseActivity<ActivityMypageNoticeBinding>(R.layout.activity_mypage_notice) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener { finish() }
    }
}