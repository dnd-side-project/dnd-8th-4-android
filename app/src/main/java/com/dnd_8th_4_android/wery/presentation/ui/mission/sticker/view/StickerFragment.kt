package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.content.Intent
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.databinding.FragmentStickerBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter.StickerAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.viewmodel.StickerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StickerFragment : BaseFragment<FragmentStickerBinding>(R.layout.fragment_sticker) {
    private val viewModel: StickerViewModel by viewModels()
    private lateinit var stickerAdapter: StickerAdapter

    companion object {
        const val STICKER_GROUP_ID = "sticker_group_id"
    }

    override fun initStartView() {
        viewModel.getMissionStatus()

        stickerAdapter = StickerAdapter { data -> moveToStickerDetail(data) }
        binding.rvSticker.adapter = stickerAdapter
    }

    override fun initDataBinding() {
        viewModel.missionStatusList.observe(viewLifecycleOwner) {
            stickerAdapter.submitList(it.acquisitionStickerInfo)

            binding.tvStartLevel.text =
                resources.getString(R.string.sticker_main_level, it.currMissionInfo.mainLevel)
            binding.tvEndLevel.text =
                resources.getString(R.string.sticker_main_level, it.currMissionInfo.mainLevel + 1)

            binding.stickerProgressBar.progress = it.currMissionInfo.progressBarRange

            when (it.currMissionInfo.subLevel) {
                0.5F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                }

                1F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.ivStart.isSelected = true
                }
                1.5F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.ivStart.isSelected = true
                }

                2F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.ivStart.isSelected = true
                    binding.ivMiddle.isSelected = true
                }
                2.5F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.ivStart.isSelected = true
                    binding.ivMiddle.isSelected = true
                }

                3F -> {
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.tvEndLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.ivStart.isSelected = true
                    binding.ivMiddle.isSelected = true
                    binding.ivEnd.isSelected = true
                }
            }
            binding.tvStickerCnt.text = it.acquisitionStickerInfo.count { stickerCount ->
                stickerCount.isStickerLocked
            }.toString()
        }
    }

    override fun initAfterBinding() {
//        StickerInfoBottomDialog("위어리의 알유 위어리", 2) {
//            moveToStickerDetail(null)
//        }.show(
//            parentFragmentManager,
//            null
//        )
    }

    private fun moveToStickerDetail(stickerGroupId: Int) {
        Intent(requireContext(), StickerDetailActivity::class.java).apply {
            putExtra(STICKER_GROUP_ID, stickerGroupId)
            startActivity(this)
        }
    }
}