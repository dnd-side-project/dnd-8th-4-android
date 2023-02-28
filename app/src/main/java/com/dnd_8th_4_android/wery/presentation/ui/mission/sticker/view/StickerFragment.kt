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

    override fun initStartView() {
        viewModel.getMissionStatus()

        stickerAdapter = StickerAdapter { data -> moveToStickerDetail(data) }
        binding.rvSticker.adapter = stickerAdapter
    }

    override fun initDataBinding() {
        viewModel.missionStatusList.observe(viewLifecycleOwner) {
            stickerAdapter.submitList(it.acquisitionStickerInfo)

//            binding.stickerProgressBar.progress =
//                ((it.currMissionInfo.subLevel - it.currMissionInfo.remainToUpMainLevel) / it.currMissionInfo.subLevel) * 100

            when (it.currMissionInfo.mainLevel) {
                1 -> {
                    binding.ivStart.isSelected = true
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                }
                2 -> {
                    binding.ivStart.isSelected = true
                    binding.ivMiddle.isSelected = true
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                }
                3 -> {
                    binding.ivStart.isSelected = true
                    binding.ivMiddle.isSelected = true
                    binding.ivEnd.isSelected = true
                    binding.tvStartLevel.setTextColor(requireContext().getColor(R.color.black))
                    binding.tvEndLevel.setTextColor(requireContext().getColor(R.color.black))
                }
            }

            binding.tvStickerCnt.text = it.acquisitionStickerInfo.size.toString()
        }
    }

    override fun initAfterBinding() {
        // TODO 서버에서 들어오는 값에 따라 분기처리
        // StickerInfoBottomDialog("위어리의 알유 위어리", 2){moveToStickerDetail(null)}.show(parentFragmentManager, null)
    }

    private fun moveToStickerDetail(data: ResponseSticker.Data.AcquisitionStickerInfo) {
        val intent = Intent(requireContext(), StickerDetailActivity::class.java)
        startActivity(intent)
    }
}