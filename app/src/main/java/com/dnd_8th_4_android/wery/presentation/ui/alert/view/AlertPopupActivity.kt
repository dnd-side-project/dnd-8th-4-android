package com.dnd_8th_4_android.wery.presentation.ui.alert.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityAlertPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view.MyMissionFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view.StickerFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertPopupActivity : BaseActivity<ActivityAlertPopupBinding>(R.layout.activity_alert_popup) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAfterBinding()
    }

    private fun initAfterBinding() {
        getInviteTab()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) getInviteTab()
                else getNotificationTab()
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun getInviteTab() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.alert_popup_frame_layout, FragmentAlertInvite())
            .commitAllowingStateLoss()
    }

    private fun getNotificationTab() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.alert_popup_frame_layout, FragmentAlertNotification())
            .commitAllowingStateLoss()
    }
}