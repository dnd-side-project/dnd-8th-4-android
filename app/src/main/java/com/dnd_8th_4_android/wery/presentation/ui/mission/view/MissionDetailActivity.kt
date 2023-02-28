package com.dnd_8th_4_android.wery.presentation.ui.mission.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.databinding.ActivityMissionDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.viewmodel.MissionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MissionDetailActivity :
    BaseActivity<ActivityMissionDetailBinding>(R.layout.activity_mission_detail) {
    private val viewModel: MissionDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initDataBinding() {
        viewModel.missionDetail.observe(this) {
            binding.layoutMissionDelete.isVisible =
                it.createUserId == AuthLocalDataSource(this).userId

            binding.tvMissionName.text = it.missionName

            binding.ivFriendImage.clipToOutline = true
//            Glide.with(this).load(it.작성자프로필)
//                .into(binding.ivFriendImage)

//            binding.tvWriterName.text = it.작성자명
            binding.tvMissionPlaceName.text = it.missionLocationName
//            binding.tvMissionPlaceAdress.text = it.미션주소

            binding.tvMissionDue.text = if (it.existPeriod) {
                resources.getString(
                    R.string.mission_detail_period,
                    it.missionStartDate,
                    it.missionEndDate
                )
            } else {
                resources.getString(R.string.mission_detail_no_period, it.missionStartDate)
            }

            binding.tvGroupName.text = it.groupName

            binding.ivGroupImage.clipToOutline = true
            Glide.with(this).load(it.groupImageUrl)
                .into(binding.ivGroupImage)

            // TODO
//            if (it.userAssignMissionInfoList.locationCheck) {
//                binding.btnMissionDetail.text = resources.getString(R.string.mission_detail_certify)
//            } else {
//                binding.btnMissionDetail.text = resources.getString(R.string.mission_detail_write)
//            }
        }
    }

    private fun initStartView() {
        viewModel.isMissionId.value = intent.getIntExtra("missionId", 0)

        viewModel.getMissionDetail()
    }

    private fun initAfterBinding() {

    }
}