package com.dnd_8th_4_android.wery.presentation.ui.onboard.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityOnboardingBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.onboard.adapter.OnBoardingAdapter
import dagger.hilt.android.AndroidEntryPoint

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        val imgList = mutableListOf(R.drawable.bg_no_group,
            androidx.core.R.drawable.notification_bg,R.drawable.bg_crying_face)
        onBoardingAdapter = OnBoardingAdapter(imgList)
        binding.vpOnboarding.adapter = onBoardingAdapter
        binding.vpIndicator.attachTo(binding.vpOnboarding)
    }

}