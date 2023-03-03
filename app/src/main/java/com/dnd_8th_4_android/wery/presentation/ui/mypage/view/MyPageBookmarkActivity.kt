package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageBookmarkBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.adapter.MyPageBookmarkRecyclerViewAdapter

class MyPageBookmarkActivity : BaseActivity<ActivityMypageBookmarkBinding>(R.layout.activity_mypage_bookmark) {
    private lateinit var myPageBookmarkRecyclerViewAdapter: MyPageBookmarkRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {

    }
}