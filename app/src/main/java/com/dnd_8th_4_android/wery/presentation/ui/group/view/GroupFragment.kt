package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.content.Intent
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupBookmarkRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.GroupViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.view.HomeFragment
import com.dnd_8th_4_android.wery.presentation.ui.search.view.SearchPostActivity
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
            setBookmarkClickListener {
                viewModel.setBookmark(it)
            }
        }
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog(requireContext())
            else dismissLoadingDialog()
        }

        viewModel.isExistBookmarkGroup.observe(viewLifecycleOwner) { isExistGroup ->
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
        binding.tvSearch.setOnClickListener {
            Intent(requireContext(), SearchPostActivity::class.java).apply {
                putExtra(HomeFragment.GROUP_ALL_LIST, viewModel.groupAllIdList.value)
                startActivity(this)
            }
        }

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), CreateGroupActivity::class.java))
        }
    }
}