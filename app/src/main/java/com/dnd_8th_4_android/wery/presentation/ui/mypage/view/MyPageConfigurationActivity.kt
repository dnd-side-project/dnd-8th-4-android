package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageConfigurationBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageConfigurationActivity :
    BaseActivity<ActivityMypageConfigurationBinding>(R.layout.activity_mypage_configuration) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener { finish() }
    }
}