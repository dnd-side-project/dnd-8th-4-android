package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageCommentConstructionBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageCommentConstructionActivity :
    BaseActivity<ActivityMypageCommentConstructionBinding>(R.layout.activity_mypage_comment_construction) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener { finish() }
    }
}