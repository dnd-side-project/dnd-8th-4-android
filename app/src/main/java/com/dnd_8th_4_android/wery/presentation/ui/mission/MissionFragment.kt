package com.dnd_8th_4_android.wery.presentation.ui.mission

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentMissionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view.MyMissionFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view.StickerFragment
import com.google.android.material.tabs.TabLayout

class MissionFragment : BaseFragment<FragmentMissionBinding>(R.layout.fragment_mission) {

    override fun initStartView() {}

    override fun initDataBinding() {}

    override fun initAfterBinding() {
        getMyMissionTab()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position == 0) getMyMissionTab()
                else getStickerTab()
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun getMyMissionTab() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mission_frame_layout, MyMissionFragment())
            .commitAllowingStateLoss()
    }

    private fun getStickerTab() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mission_frame_layout, StickerFragment())
            .commitAllowingStateLoss()
    }
}