package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageMissionBinding
import com.dnd_8th_4_android.wery.databinding.ActivityMypageMissionConstructionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageMissionConstructionActivity :
    BaseActivity<ActivityMypageMissionConstructionBinding>(R.layout.activity_mypage_mission_construction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }
}