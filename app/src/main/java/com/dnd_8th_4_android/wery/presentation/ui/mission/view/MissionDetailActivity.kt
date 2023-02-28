package com.dnd_8th_4_android.wery.presentation.ui.mission.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMissionDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MissionDetailActivity :
    BaseActivity<ActivityMissionDetailBinding>(R.layout.activity_mission_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showToast(intent.getIntExtra("missionId",0).toString())
    }
}