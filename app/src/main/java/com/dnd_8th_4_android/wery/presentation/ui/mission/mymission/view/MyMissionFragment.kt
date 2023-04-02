package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentMyMissionBinding
import com.dnd_8th_4_android.wery.domain.model.RecommendMission
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.create.CreateMissionActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.MissionCardAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.RecommendMissionAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.viewmodel.MyMissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MyMissionFragment : BaseFragment<FragmentMyMissionBinding>(R.layout.fragment_my_mission) {

    private lateinit var missionCardAdapter: MissionCardAdapter
    private lateinit var recommendMissionAdapter: RecommendMissionAdapter
    private val viewModel: MyMissionViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
        initRecommendMissionAdapter()
        initMissionCardAdapter()
    }

    override fun initDataBinding() {
        viewModel.progressMainMissionList.observe(viewLifecycleOwner) {
            missionCardAdapter.submitList(it)
            binding.tvMissionCnt.text = it.size.toString()
            viewModel.setGroupExistState(it.isNotEmpty())
        }
        viewModel.groupExistState.observe(viewLifecycleOwner) {
            if (it == true) binding.layoutExist.visibility = View.VISIBLE
            else binding.layoutNoExist.visibility = View.VISIBLE
        }
    }

    override fun initAfterBinding() {
        binding.includeMissionExist.layoutProgressMission.setOnClickListener {
            startActivity(Intent(requireContext(), MissionProgressActivity::class.java))
        }
        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), CreateMissionActivity::class.java))
        }
    }

    private fun initMissionCardAdapter() {
        missionCardAdapter = MissionCardAdapter()
        binding.includeMissionExist.rvMissionExist.apply {
            adapter = missionCardAdapter
            itemAnimator = null
        }
    }

    private fun initRecommendMissionAdapter() {
        recommendMissionAdapter = RecommendMissionAdapter()

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val startDate = current.format(formatter)
        val endDate = current.plusWeeks(1).format(formatter)

        recommendMissionAdapter.itemList = mutableListOf<RecommendMission>(
            RecommendMission("서울숲 전시회 가서 교양 쌓기 \uD83C\uDFA8", startDate, endDate),
            RecommendMission("새로 나온 영화 보러 가기 \uD83C\uDF7F️", startDate, endDate),
            RecommendMission("한강에서 돗자리 깔고 치맥 하기 \uD83C\uDF7A", startDate, endDate),
        )
        binding.rvRecommendMission.adapter = recommendMissionAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyMissionList()
    }
}