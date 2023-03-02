package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityProfileChangeBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_IMAGE
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_NAME
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.ProfileChangeViewModel

class ProfileChangeActivity :
    BaseActivity<ActivityProfileChangeBinding>(R.layout.activity_profile_change) {
    private val viewModel: ProfileChangeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.vm = viewModel

        binding.ivImage.clipToOutline = true
        Glide.with(this).load(intent.getStringExtra(USER_IMAGE))
            .into(binding.ivImage)

        binding.etNickname.setText(intent.getStringExtra(USER_NAME))

        viewModel.setTextCount(intent.getStringExtra(USER_NAME)!!.length)
    }

    private fun initDataBinding() {
        viewModel.textCount.observe(this) {
            if (it in 0..14) {
                binding.tvCount.setTextColor(ContextCompat.getColor(this, R.color.gray600))
                binding.etNickname.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_et_my_page_change)
            } else {
                binding.tvCount.setTextColor(ContextCompat.getColor(this, R.color.color_eb0555))
                binding.etNickname.background =
                    ContextCompat.getDrawable(this, R.drawable.shape_white_radius_8_eb0555)
                binding.tvNicknameError.isVisible = true
            }
        }

        viewModel.isEnabled.observe(this) {
            binding.tvSuccess.isEnabled = it
        }
    }

    private fun initAfterBinding() {
        binding.etNickname.setOnFocusChangeListener { _, gainFocus ->
            if (gainFocus) {
                binding.tvSuccess.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }

        binding.ivEdtDelete.setOnClickListener {
            binding.etNickname.text.clear()
        }

        binding.tvSuccess.setOnClickListener {
            if (viewModel.isEnabled.value == true) {
                // 다이얼로그 추가
            }
        }
    }
}