package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityStickerDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter.StickerDetailAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.viewmodel.StickerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StickerDetailActivity :
    BaseActivity<ActivityStickerDetailBinding>(R.layout.activity_sticker_detail) {
    private val viewModel: StickerDetailViewModel by viewModels()
    private lateinit var stickerDetailAdapter: StickerDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMissionDetail(intent.getIntExtra(StickerFragment.STICKER_GROUP_ID, -1))
        initDataBinding()
        initBackEvent()
    }

    private fun initDataBinding() {
        viewModel.isLoading.observe(this) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        viewModel.stickerData.observe(this) {
            Glide.with(this).load(it.stickerGroupThumbnailUrl)
                .into(binding.ivStickerImg)

            binding.tvStickerName.text = it.stickerGroupName
            binding.tvStickerLevel.text = resources.getString(R.string.sticker_main_level, it.stickerGroupLevel)

            stickerDetailAdapter = StickerDetailAdapter(it.stickerInfoList)
            binding.rvSticker.adapter = stickerDetailAdapter
        }
    }

    private fun initBackEvent() {
        binding.ivBack.setOnClickListener { finish() }
    }
}