package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.content.Intent
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.databinding.FragmentStickerBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter.StickerAdapter

class StickerFragment : BaseFragment<FragmentStickerBinding>(R.layout.fragment_sticker) {

    private lateinit var stickerAdapter: StickerAdapter

    override fun initStartView() {
        stickerAdapter = StickerAdapter { data -> moveToStickerDetail(data) }
        stickerAdapter.submitList(
            listOf(
                ResponseSticker(
                    false, "위어리 알유 위어리?", "",
                    2, "무제한"
                ),
                ResponseSticker(
                    true, "위어리 내가 짱임!", "",
                    5, "무제한"
                )
            )
        )
        binding.rvSticker.adapter = stickerAdapter
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {

    }

    private fun moveToStickerDetail(data: ResponseSticker) {
        val intent = Intent(requireContext(), StickerDetailActivity::class.java)
        startActivity(intent)
    }
}