package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupBookmarkRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.GroupViewModel
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupFragment : BaseFragment<FragmentGroupBinding>(R.layout.fragment_group) {
    private val viewModel: GroupViewModel by viewModels()

    private lateinit var groupRecyclerViewAdapter: GroupBookmarkRecyclerViewAdapter
    private lateinit var groupListViewAdapter: GroupListRecyclerViewAdapter

    override fun initStartView() {
        binding.vm = viewModel

        viewModel.getBookmarkList()
        viewModel.getSignGroup()

        groupListViewAdapter = GroupListRecyclerViewAdapter()
        binding.rvGroupList.adapter = groupListViewAdapter
        binding.rvGroupList.itemAnimator = null

        groupListViewAdapter.apply {
//          setBookmarkClickListener {
//              viewModel.setUpdateBookmark(it, groupBookmarkData, groupList)
//          }
        }
    }

    override fun initDataBinding() {
        viewModel.isExistGroup.observe(viewLifecycleOwner) { isExistGroup ->
            if (isExistGroup) {
                groupRecyclerViewAdapter = GroupBookmarkRecyclerViewAdapter()
                binding.activityGroupBookmark.rvGroupBookmarkList.apply {
                    itemAnimator = null
                    adapter = groupRecyclerViewAdapter
                }
            }
        }

        viewModel.bookmarkList.observe(viewLifecycleOwner) {
            groupRecyclerViewAdapter.submitList(it)
        }

        viewModel.groupList.observe(viewLifecycleOwner) {
            groupListViewAdapter.submitList(it)
            binding.tvGroupListCount.text = it.size.toString()
        }
    }

    override fun initAfterBinding() {
        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                binding.etSearch.hideKeyboard()
                binding.etSearch.clearFocus()
                // TODO 검색 동작
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.etSearch.showKeyboard()
        }

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), CreateGroupActivity::class.java))
        }
    }
}