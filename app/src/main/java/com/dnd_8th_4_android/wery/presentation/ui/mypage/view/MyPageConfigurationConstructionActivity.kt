package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageConfigurationConstructionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageConfigurationConstructionActivity :
    BaseActivity<ActivityMypageConfigurationConstructionBinding>(R.layout.activity_mypage_configuration_construction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener { finish() }
    }
}