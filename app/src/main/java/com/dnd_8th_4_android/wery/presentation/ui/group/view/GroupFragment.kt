package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupListData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.FragmentGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupBookmarkRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.GroupViewModel
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard

class GroupFragment : BaseFragment<FragmentGroupBinding>(R.layout.fragment_group) {
    private val viewModel: GroupViewModel by viewModels()

    private lateinit var groupRecyclerViewAdapter: GroupBookmarkRecyclerViewAdapter
    private lateinit var groupListViewAdapter: GroupListRecyclerViewAdapter

    private lateinit var groupBookmarkData: MutableList<ResponseGroupData.Data>
    private lateinit var groupList: List<ResponseGroupListData.Data>

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.isExistGroup.observe(viewLifecycleOwner) { isExistGroup ->
            if (isExistGroup) {
                makeList()
                groupRecyclerViewAdapter = GroupBookmarkRecyclerViewAdapter()
                groupRecyclerViewAdapter.submitList(groupBookmarkData)
                binding.activityGroupBookmark.rvGroupBookmarkList.apply {
                    itemAnimator = null
                    adapter = groupRecyclerViewAdapter
                    addItemDecoration(
                        MarginItemDecoration(
                            resources.getDimension(R.dimen.groupList_item_margin).toInt()
                        )
                    )
                }

                groupListViewAdapter = GroupListRecyclerViewAdapter()
                groupListViewAdapter.submitList(groupList)
                binding.rvGroupList.adapter = groupListViewAdapter
                binding.rvGroupList.itemAnimator = null
                viewModel.groupCount.value = groupList.size

                groupListViewAdapter.apply {
                    setBookmarkClickListener {
                        viewModel.setUpdateBookmark(it, groupBookmarkData, groupList)
                    }
                }
            } else {
                // TODO 북마크한 그룹이 없는 경우
            }
        }

        viewModel.isUpdateBookmark.observe(viewLifecycleOwner) {
            groupListViewAdapter.submitList(it.toMutableList())
            groupRecyclerViewAdapter.submitList(viewModel.isUpdateGroup.value!!.toMutableList())
            groupList = it
            groupBookmarkData = viewModel.isUpdateGroup.value!!
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

    private fun makeList() {
        groupBookmarkData = mutableListOf()

        groupList = arrayListOf(
            ResponseGroupListData.Data(
                1,
                R.drawable.img_no_group,
                "산본 솜주먹11",
                "1111소개글은 최대 12자까지만",
                10,
                false
            ),
            ResponseGroupListData.Data(
                2,
                R.drawable.img_no_group,
                "산본 솜주먹22",
                "2222소개글은 최대 12자까지만111111",
                20,
                false
            ),
            ResponseGroupListData.Data(
                3,
                R.drawable.img_no_group,
                "산본 솜주먹33",
                "33333소개글은 최대 12자까지만111111",
                30,
                false
            ),
        )
    }
}