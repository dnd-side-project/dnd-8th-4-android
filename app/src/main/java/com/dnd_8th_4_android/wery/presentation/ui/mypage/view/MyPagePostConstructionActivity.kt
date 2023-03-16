package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypagePostConstructionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPagePostConstructionActivity :
    BaseActivity<ActivityMypagePostConstructionBinding>(R.layout.activity_mypage_post_construction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }
}