package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.view

import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMissionProgressBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.adapter.MyMissionAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.viewmodel.MissionProgressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MissionProgressActivity :
    BaseActivity<ActivityMissionProgressBinding>(R.layout.activity_mission_progress) {

    private lateinit var myMissionAdapter: MyMissionAdapter
    private val viewModel: MissionProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSearchPost()
    }

    private fun initStartView() {
        binding.vm = viewModel
        myMissionAdapter = MyMissionAdapter()
        binding.rvProgressMission.adapter = myMissionAdapter
    }

    private fun initDataBinding() {
        viewModel.progressMissionList.observe(this) {
            myMissionAdapter.submitList(it)
            binding.tvMissionCnt.text = it.size.toString()
        }
    }

    private fun initAfterBinding() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

}