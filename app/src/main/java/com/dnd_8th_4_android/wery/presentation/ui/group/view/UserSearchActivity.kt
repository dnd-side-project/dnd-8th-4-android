package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchUserInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.UserSearchRecyclerViewAdapter

class UserSearchActivity :
    BaseActivity<ActivitySearchUserInviteBinding>(R.layout.activity_search_user_invite) {
    private lateinit var userSearchRecyclerViewAdapter: UserSearchRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {

    }

    private fun initStartView() {
        userSearchRecyclerViewAdapter = UserSearchRecyclerViewAdapter()
        binding.rvSearch.apply {
            adapter = userSearchRecyclerViewAdapter
            itemAnimator = null
        }
    }
}