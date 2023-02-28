package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityStickerDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter.StickerDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StickerDetailActivity :
    BaseActivity<ActivityStickerDetailBinding>(R.layout.activity_sticker_detail) {
    private lateinit var stickerDetailAdapter: StickerDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initBackEvent()
    }

    private fun initAdapter() {
        stickerDetailAdapter = StickerDetailAdapter()
        for (i in 0..10) {
            stickerDetailAdapter.itemList.add("")
        }
        binding.rvSticker.adapter = stickerDetailAdapter
    }

    private fun initBackEvent() {
        binding.ivBack.setOnClickListener { finish() }
    }


}