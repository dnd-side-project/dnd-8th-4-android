package com.dnd_8th_4_android.wery.presentation.ui.onboard.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityOnboardingBinding
import com.dnd_8th_4_android.wery.domain.model.OnBoardData
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.onboard.adapter.OnBoardingAdapter
import com.dnd_8th_4_android.wery.presentation.ui.onboard.viewmodel.OnBoardingViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initAfterBinding()
    }

    private fun initStartView() {
        setOnBoardVp()
    }

    private fun setOnBoardList():MutableList<OnBoardData> {
        val onBoardList = mutableListOf<OnBoardData>()
        onBoardList.add(OnBoardData(ContextCompat.getDrawable(this,R.drawable.img_onboard_one)!!,resources.getString(R.string.onBoard_title_one),resources.getString(R.string.onBoard_content_one)))
        onBoardList.add(OnBoardData( ContextCompat.getDrawable(this,R.drawable.img_onboard_two)!!,resources.getString(R.string.onBoard_title_two),resources.getString(R.string.onBoard_content_two)))
        onBoardList.add(OnBoardData( ContextCompat.getDrawable(this,R.drawable.img_onboard_three)!!,resources.getString(R.string.onBoard_title_three),resources.getString(R.string.onBoard_content_three)))
        onBoardList.add(OnBoardData( ContextCompat.getDrawable(this,R.drawable.img_onboard_four)!!,resources.getString(R.string.onBoard_title_four),resources.getString(R.string.onBoard_content_four)))
        return onBoardList
    }

    private fun setOnBoardVp() {
        onBoardingAdapter = OnBoardingAdapter()
        onBoardingAdapter.itemList = setOnBoardList()
        binding.vpOnboarding.adapter = onBoardingAdapter
        binding.vpIndicator.attachTo(binding.vpOnboarding)
    }

    private fun initAfterBinding() {
        setOnBoardVpEvent()
        setNextBtnClickListener()
    }

    private fun setOnBoardVpEvent() {
        binding.vpOnboarding.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onBoardingViewModel.setCurrentPos(position)
            }
        })
    }

    private fun setNextBtnClickListener() {
        binding.btnNext.setOnClickListener {
            when (onBoardingViewModel.currentPosition.value) {
                0 -> binding.vpOnboarding.currentItem = 1
                1 -> binding.vpOnboarding.currentItem = 2
                2 -> binding.vpOnboarding.currentItem = 3
                3 -> {
                    finish()
                    onBoardingViewModel.saveOnBoardingState()
                    startActivity(Intent(this, SignActivity::class.java))
                }
            }
        }
    }
}