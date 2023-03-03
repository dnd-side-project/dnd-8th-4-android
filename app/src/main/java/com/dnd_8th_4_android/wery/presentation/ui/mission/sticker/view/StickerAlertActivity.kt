package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityAlertStickerBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.viewmodel.StickerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StickerAlertActivity :
    BaseActivity<ActivityAlertStickerBinding>(R.layout.activity_alert_sticker) {

    companion object {
        const val STICKER_GROUP_ID = "sticker_group_id"
    }

    private val viewModel: StickerDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        viewModel.getMissionDetail(intent.getIntExtra(STICKER_GROUP_ID, 1))
    }

    private fun initDataBinding() {
        viewModel.stickerData.observe(this) {
            binding.data = it
        }
    }

    private fun initAfterBinding() {
        binding.btnShowSticker.setOnClickListener {
            Intent(this, StickerDetailActivity::class.java).apply {
                putExtra(STICKER_GROUP_ID, intent.getIntExtra(STICKER_GROUP_ID, 1))
                startActivity(this)
            }
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

}