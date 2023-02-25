package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionCard
import com.dnd_8th_4_android.wery.databinding.ActivityMissionProgressBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.MyMissionAdapter

class MissionProgressActivity :
    BaseActivity<ActivityMissionProgressBinding>(R.layout.activity_mission_progress) {

    private lateinit var myMissionAdapter: MyMissionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myMissionAdapter = MyMissionAdapter()
        myMissionAdapter.submitList(
            listOf(
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                ),
                ResponseMyMissionCard(
                    0,
                    "대충 미션 제목입니다",
                    "뉴진스와 아이들",
                    "",
                    "2022.02.12",
                    "2023.01.01"
                )
            )
        )
        binding.rvProgressMission.adapter = myMissionAdapter

        binding.ivBack.setOnClickListener {
            finish()
        }
    }



}