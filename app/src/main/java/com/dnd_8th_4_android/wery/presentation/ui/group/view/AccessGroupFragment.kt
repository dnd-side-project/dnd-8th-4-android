package com.dnd_8th_4_android.wery.presentation.ui.group.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseAccessGroupData
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter

    private lateinit var missionList: MutableList<ResponseAccessGroupData.Data>

    override fun initStartView() {

    }

    override fun initDataBinding() {
        // TODO 미션이 있는지 없는지 판별
        makeList()
        accessGroupMissionRecyclerViewAdapter = AccessGroupMissionRecyclerViewAdapter()
        accessGroupMissionRecyclerViewAdapter.submitList(missionList)
        binding.layoutYesMission.rvYesMission.apply {
            adapter = accessGroupMissionRecyclerViewAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.accessGroup_mission_item_margin).toInt()
                )
            )
        }
    }

    override fun initAfterBinding() {

    }

    private fun makeList() {
        missionList = mutableListOf(
            ResponseAccessGroupData.Data(
                1,
                "D-11",
                "홍대 기범 생카 가서 예절샷 찍기1",
                "11.11.11",
                "22.22.22",
                true
            ),
            ResponseAccessGroupData.Data(
                2,
                "D-22",
                "홍대 기범 생카 가서 예절샷 찍기1",
                "33.33.33",
                "44.44.44",
                false
            ),
            ResponseAccessGroupData.Data(
                1,
                "D-11",
                "홍대 기범 생카 가서 예절샷 찍기1",
                "55.55.55",
                "66.66.66",
                false
            )
        )
    }
}