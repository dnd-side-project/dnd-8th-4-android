package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageBookmarkBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.adapter.MyPageBookmarkRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.MyPageBookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageBookmarkActivity : BaseActivity<ActivityMypageBookmarkBinding>(R.layout.activity_mypage_bookmark) {
    private val viewModel: MyPageBookmarkViewModel by viewModels()
    private lateinit var myPageBookmarkRecyclerViewAdapter: MyPageBookmarkRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initStartView() {
        viewModel.getMyBookmarkList()
    }

    private fun initDataBinding() {
        viewModel.myBookmarkData.observe(this) {
            binding.tvPostCount.text = it.size.toString()

            myPageBookmarkRecyclerViewAdapter = MyPageBookmarkRecyclerViewAdapter(it)
            binding.rvBookmark.adapter = myPageBookmarkRecyclerViewAdapter
        }
    }
}