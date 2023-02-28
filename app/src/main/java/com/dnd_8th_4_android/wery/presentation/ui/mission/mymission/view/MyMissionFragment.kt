package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view

import android.content.Intent
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentMyMissionBinding
import com.dnd_8th_4_android.wery.domain.model.RecommendMission
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.MissionCardAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.RecommendMissionAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.viewmodel.MyMissionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyMissionFragment : BaseFragment<FragmentMyMissionBinding>(R.layout.fragment_my_mission) {

    private lateinit var missionCardAdapter: MissionCardAdapter
    private lateinit var recommendMissionAdapter: RecommendMissionAdapter
    private val viewModel: MyMissionViewModel by viewModels()

    override fun initStartView() {
        initMissionCardAdapter()
        initRecommendMissionAdapter()
    }

    override fun initDataBinding() {
        viewModel.progressMainMissionList.observe(viewLifecycleOwner) {
            missionCardAdapter.submitList(it)
            binding.tvMissionCnt.text = it.size.toString()
        }
    }

    override fun initAfterBinding() {
        binding.includeMissionExist.layoutProgressMission.setOnClickListener {
            startActivity(Intent(requireContext(), MissionProgressActivity::class.java))
        }
    }

    private fun initMissionCardAdapter() {
        missionCardAdapter = MissionCardAdapter()
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

    override fun onResume() {
        super.onResume()
        viewModel.getMyMissionList()
    }
}