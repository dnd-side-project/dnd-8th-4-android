package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageBookmarkBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.detail.view.PostDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter.Companion.CONTENT
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter.Companion.CONTENT_ID
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter.Companion.GROUP_NAME
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter.Companion.TIME
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter.Companion.USER_IMAGE
import com.dnd_8th_4_android.wery.presentation.ui.mypage.adapter.MyPageBookmarkRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.MyPageBookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageBookmarkActivity :
    BaseActivity<ActivityMypageBookmarkBinding>(R.layout.activity_mypage_bookmark) {
    private val viewModel: MyPageBookmarkViewModel by viewModels()
    private lateinit var myPageBookmarkRecyclerViewAdapter: MyPageBookmarkRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initAfterBinding()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyBookmarkList()
    }

    private fun initDataBinding() {
        viewModel.myBookmarkData.observe(this) {
            binding.tvPostCount.text = it.size.toString()

            myPageBookmarkRecyclerViewAdapter = MyPageBookmarkRecyclerViewAdapter(it)
            myPageBookmarkRecyclerViewAdapter.setGoPostClickListener { position ->
                Intent(this, PostDetailActivity::class.java).apply {
                    putExtra(CONTENT_ID, it[position].contentId)
                    putExtra(CONTENT, it[position].content)
                    putExtra(USER_IMAGE, it[position].profileImageUrl)
                    putExtra(GROUP_NAME, it[position].groupName)
                    putExtra(TIME, it[position].createAt)
                    startActivity(this)
                }
            }
            binding.rvBookmark.adapter = myPageBookmarkRecyclerViewAdapter
        }
    }

    private fun initAfterBinding() {
        binding.ivBack.setOnClickListener { finish() }
    }
}