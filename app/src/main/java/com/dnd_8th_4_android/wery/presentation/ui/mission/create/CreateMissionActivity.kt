package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import android.os.Bundle
import android.view.View
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class CreateMissionActivity :
    BaseActivity<ActivityCreateMissionBinding>(R.layout.activity_create_mission) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.missionTabLayout.apply {
            addTab(this.newTab().setText(resources.getString(R.string.create_mission_due_exist)))
            addTab(this.newTab().setText(resources.getString(R.string.create_mission_due_no_exist)))
        }
    }

    private fun initAfterBinding() {
        binding.missionTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.contains("있음")) binding.layoutDate.visibility = View.VISIBLE
                else binding.layoutDate.visibility = View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}