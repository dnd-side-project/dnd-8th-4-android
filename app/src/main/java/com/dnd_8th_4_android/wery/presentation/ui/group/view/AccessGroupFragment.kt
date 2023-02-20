package com.dnd_8th_4_android.wery.presentation.ui.group.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseAccessGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.AccessGroupViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private val viewModel: AccessGroupViewModel by viewModels()
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private lateinit var missionList: MutableList<ResponseAccessGroupData.Data>
    private lateinit var postList: MutableList<ResponsePostData.Data>

    override fun initStartView() {
        binding.vm = viewModel

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
        viewModel.isExistMission.observe(viewLifecycleOwner) { isExistMission ->
            if (isExistMission) {
                binding.tvMissionIngCount.isVisible = true
                binding.tvMissionIngCount.text = postList.size.toString()

                val pagerWidth =
                    resources.getDimensionPixelOffset(R.dimen.accessGroup_viewPager_width)
                val screenWidth = resources.displayMetrics.widthPixels
                val pagerPadding = ((screenWidth - pagerWidth) * 0.5).toInt()

                binding.layoutYesMission.vpYesMission.clipToPadding = false
                binding.layoutYesMission.vpYesMission.clipChildren = false
                binding.layoutYesMission.vpYesMission.setPadding(
                    14,
                    0,
                    pagerPadding,
                    0
                )
                binding.layoutYesMission.vpYesMission.setPageTransformer { page, _ ->
                    page.translationX =
                        resources.getDimensionPixelOffset(R.dimen.view_pager_offset_12).toFloat()
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
            } else {
                binding.tvMissionIngCount.isVisible = true
            }
        }

        viewModel.isMissionCount.observe(viewLifecycleOwner) {
            if (it != 0) {
                binding.tvMissionIngCount.isVisible = true
                binding.tvMissionIngCount.text = postList.size.toString()
            } else {
                binding.tvMissionIngCount.isVisible = false
            }
        }
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
                1,
                "홍대 기범 생카 가서 예절샷 찍기1",
                "11.11.11",
                "22.22.22",
                true
            ),
            ResponseAccessGroupData.Data(
                2,
                2,
                "홍대 기범 생카 가서 예절샷 찍기1",
                "33.33.33",
                "44.44.44",
                false
            ),
            ResponseAccessGroupData.Data(
                3,
                5,
                "홍대 기범 생카 가서 예절샷 찍기1",
                "55.55.55",
                "66.66.66",
                false
            ),
            ResponseAccessGroupData.Data(
                4,
                7,
                "홍대 기범 생카 가서 예절샷 찍기1",
                "55.55.55",
                "66.66.66",
                true
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