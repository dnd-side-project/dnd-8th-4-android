package com.dnd_8th_4_android.wery.presentation.ui.group.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseAccessGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private lateinit var missionList: MutableList<ResponseAccessGroupData.Data>
    private lateinit var postList: MutableList<ResponsePostData.Data>

    override fun initStartView() {
        makeList()

        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        postRecyclerViewAdapter.submitList(postList)
        binding.rvMyGroupPost.apply {
            adapter = postRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
    }

    override fun initDataBinding() {
        // TODO 미션이 있는지 없는지 판별

        val pagerPadding = resources.getDimensionPixelOffset(R.dimen.view_pager_padding_width)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.view_pager_offset_12)

        binding.layoutYesMission.vpYesMission.setPadding(pagerPadding, 0, pagerPadding, 0)
        binding.layoutYesMission.vpYesMission.setPageTransformer { page, position ->
            page.translationX = position * offsetPx
        }
        binding.layoutYesMission.vpYesMission.offscreenPageLimit = 1

        accessGroupMissionRecyclerViewAdapter = AccessGroupMissionRecyclerViewAdapter()
        accessGroupMissionRecyclerViewAdapter.submitList(missionList)
        binding.layoutYesMission.vpYesMission.apply {
            adapter = accessGroupMissionRecyclerViewAdapter
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.accessGroup_mission_item_margin).toInt()
                )
            )
        }
        binding.layoutYesMission.vpIndicator.attachTo(binding.layoutYesMission.vpYesMission)
    }

    override fun initAfterBinding() {
        binding.tvGroupName.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NAME)
        binding.tvGroupMemberNumber.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NUMBER)
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

        postList = arrayListOf(
            ResponsePostData.Data(
                1,
                R.drawable.img_no_group,
                "User1",
                "고잔동 맥도날드1",
                "Group1",
                "111111111피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. ",
                arrayListOf(R.drawable.img_no_group),
                mutableListOf(),
                listOf(),
                "1H:MM",
                "11",
                -1
            ),
            ResponsePostData.Data(
                2,
                R.drawable.img_no_group,
                "User2",
                "고잔동 맥도날드2",
                "Group2",
                "222222222피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. ",
                arrayListOf(R.drawable.img_no_group, R.drawable.img_crying_face),
                mutableListOf(R.drawable.img_crying_face),
                listOf("안녕하세요", "DND 여러분"),
                "2H:MM",
                "22",
                -1
            ),
            ResponsePostData.Data(
                3,
                R.drawable.img_no_group,
                "User3",
                "고잔동 맥도날드3",
                "Group3",
                "33333333피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다.",
                arrayListOf(R.drawable.img_no_group),
                mutableListOf(R.drawable.img_crying_face, R.drawable.img_crying_face),
                listOf("안녕하세요", "DND 여러분", "adsf", "dsa"),
                "3H:MM",
                "33",
                -1
            ),
            ResponsePostData.Data(
                4,
                R.drawable.img_no_group,
                "User4",
                "고잔동 맥도날드4",
                "Group4",
                "4444444444피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다.",
                arrayListOf(R.drawable.img_no_group, R.drawable.img_crying_face),
                mutableListOf(
                    R.drawable.img_crying_face,
                    R.drawable.img_crying_face,
                    R.drawable.img_crying_face
                ),
                listOf("안녕하세요", "DND 여러분", "adsf", "dsa"),
                "4H:MM",
                "44",
                -1
            ),
        )
    }
}