package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionCard
import com.dnd_8th_4_android.wery.databinding.FragmentMyMissionBinding
import com.dnd_8th_4_android.wery.domain.model.RecommendMission
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.MissionCardAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.RecommendMissionAdapter

class MyMissionFragment : BaseFragment<FragmentMyMissionBinding>(R.layout.fragment_my_mission) {

    private lateinit var missionCardAdapter: MissionCardAdapter
    private lateinit var recommendMissionAdapter:RecommendMissionAdapter
    override fun initStartView() {
        initMissionCardAdapter()
        initRecommendMissionAdapter()
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }

    private fun initMissionCardAdapter() {
        missionCardAdapter = MissionCardAdapter()
        missionCardAdapter.submitList(
            listOf(
                ResponseMissionCard(
                    0, 0, "title", "content",
                    "진우와 아이들", "", "23.02.01", "23.02.29",
                    "READY", 5
                ),
                ResponseMissionCard(
                    0, 1, "title", "content",
                    "진우와 아이들", "", "23.02.01", "23.02.29",
                    "READY", 5
                ),
                ResponseMissionCard(
                    0, 2, "title", "content",
                    "진우와 아이들", "", "23.02.01", "23.02.29",
                    "READY", -1
                )
            )
        )

        binding.includeMissionExist.rvMissionExist.adapter = missionCardAdapter
    }

    private fun initRecommendMissionAdapter() {
        recommendMissionAdapter = RecommendMissionAdapter()
        recommendMissionAdapter.itemList = mutableListOf<RecommendMission>(
            RecommendMission("서울숲 전시회 가서 교양 쌓기 \uD83C\uDFA8","2022.02.04","2022.04.12"),
            RecommendMission("스키장 가서 스키 마스터 되기 ⛷️","2022.02.04","2022.04.12"),
            RecommendMission("서울숲 전시회 가서 교양 쌓기 \uD83C\uDFA8","2022.02.04","2022.04.12"),
        )
        binding.rvRecommendMission.adapter = recommendMissionAdapter
    }
}