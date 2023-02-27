package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityGroupInformationBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupInformationRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.GroupInformationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupInformationActivity :
    BaseActivity<ActivityGroupInformationBinding>(R.layout.activity_group_information) {
    private val viewModel: GroupInformationViewModel by viewModels()
    private lateinit var groupInformationRecyclerViewAdapter: GroupInformationRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        viewModel.isSelectGroupId.value = intent.getStringExtra(GroupListRecyclerViewAdapter.GROUP_Id)
        viewModel.getGroupInformation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {
        viewModel.groupList.observe(this) {
            groupInformationRecyclerViewAdapter.submitList(it.groupUserInfoList)
        }
    }

    private fun initStartView() {
        groupInformationRecyclerViewAdapter = GroupInformationRecyclerViewAdapter()
        binding.rvGroupList.apply {
            adapter = groupInformationRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
    }
}