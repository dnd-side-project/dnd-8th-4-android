package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchUserInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.UserSearchRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.UserSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity :
    BaseActivity<ActivitySearchUserInviteBinding>(R.layout.activity_search_user_invite) {
    private val viewModel: UserSearchViewModel by viewModels()
    private lateinit var userSearchRecyclerViewAdapter: UserSearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {
        viewModel.searchUserList.observe(this) {
            userSearchRecyclerViewAdapter.submitList(it)
        }
    }

    private fun initStartView() {
        userSearchRecyclerViewAdapter = UserSearchRecyclerViewAdapter()
        binding.rvSearch.apply {
            adapter = userSearchRecyclerViewAdapter
            itemAnimator = null
        }
    }
}