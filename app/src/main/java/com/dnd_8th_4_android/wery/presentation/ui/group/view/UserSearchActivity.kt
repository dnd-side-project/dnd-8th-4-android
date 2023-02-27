package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchUserInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.UserSearchRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.UserSearchViewModel
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchActivity :
    BaseActivity<ActivitySearchUserInviteBinding>(R.layout.activity_search_user_invite) {
    private val viewModel: UserSearchViewModel by viewModels()
    private lateinit var userSearchRecyclerViewAdapter: UserSearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isSelectGroupId.value =
            intent.getStringExtra(GroupListRecyclerViewAdapter.GROUP_Id)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {
        viewModel.searchUserList.observe(this) {
            userSearchRecyclerViewAdapter.submitList(it)
        }
    }

    private fun initStartView() {
        binding.etSearch.requestFocus()
        Handler(Looper.getMainLooper())
            .postDelayed({
                binding.etSearch.showKeyboard()
            }, 400)

        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            binding.tvInvite.isEnabled = false
            viewModel.searchKeyword.value = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && viewModel.searchKeyword.value != "") {
                if (!binding.rvSearch.isVisible) {
                    binding.rvSearch.isVisible = true
                    binding.ivSearch.isVisible = false
                }
                userSearchRecyclerViewAdapter = UserSearchRecyclerViewAdapter()
                userSearchRecyclerViewAdapter.setOnUserClickListener { userId ->
                    binding.tvInvite.isEnabled = true
                    binding.tvInvite.setOnClickListener {
                        viewModel.groupInvite(userId)
                        binding.tvInvite.isEnabled = false
                    }
                }

                binding.rvSearch.apply {
                    adapter = userSearchRecyclerViewAdapter
                    itemAnimator = null
                }

                viewModel.getUserSearchList()
                binding.etSearch.hideKeyboard()
                binding.etSearch.clearFocus()
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.etSearch.requestFocus()
            binding.etSearch.showKeyboard()
        }
    }
}