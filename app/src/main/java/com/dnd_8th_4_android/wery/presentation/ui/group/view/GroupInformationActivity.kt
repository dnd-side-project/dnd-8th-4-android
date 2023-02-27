package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityGroupInformationBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupInformationRecyclerViewAdapter

class GroupInformationActivity :
    BaseActivity<ActivityGroupInformationBinding>(R.layout.activity_group_information) {
    private lateinit var groupInformationRecyclerViewAdapter: GroupInformationRecyclerViewAdapter

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
        groupInformationRecyclerViewAdapter = GroupInformationRecyclerViewAdapter()
        binding.rvGroupList.apply {
            adapter = groupInformationRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
    }
}