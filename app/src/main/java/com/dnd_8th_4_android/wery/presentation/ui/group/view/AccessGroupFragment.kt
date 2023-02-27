package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentAccessGroupBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.AccessGroupMissionRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.AccessGroupViewModel
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.view.HomeFragment
import com.dnd_8th_4_android.wery.presentation.ui.search.view.SearchPostActivity
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccessGroupFragment :
    BaseFragment<FragmentAccessGroupBinding>(R.layout.fragment_access_group) {
    private val viewModel: AccessGroupViewModel by viewModels()
    private lateinit var accessGroupMissionRecyclerViewAdapter: AccessGroupMissionRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    override fun onResume() {
        super.onResume()
        binding.vm = viewModel

        viewModel.setLoading()
        viewModel.isSelectGroupId.value =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_Id)
        viewModel.initBookmark(requireArguments().getBoolean(GroupListRecyclerViewAdapter.GROUP_BOOKMARK))
        viewModel.getGroupPost()
        viewModel.getMission()
    }

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener { contentId, postMine, isSelected ->
                val bottomSheet = PopupBottomDialog(contentId, postMine, isSelected)
                bottomSheet.setOnBookmarkListener {
                    viewModel.getGroupPost()
                }
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { view, position, contentId ->
                getGradePopUp(view, position, contentId)
            }
        }

        binding.rvMyGroupPost.apply {
            adapter = postRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog(requireContext())
            else dismissLoadingDialog()
        }

        viewModel.isSelectedBookmark.observe(viewLifecycleOwner) {
            binding.ivBookmark.isSelected = it
        }

        viewModel.isExistMission.observe(viewLifecycleOwner) { isExistMission ->
            if (isExistMission) {
                binding.tvMissionIngCount.isVisible = true
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
                binding.layoutYesMission.vpYesMission.adapter =
                    accessGroupMissionRecyclerViewAdapter
                binding.layoutYesMission.vpIndicator.attachTo(binding.layoutYesMission.vpYesMission)
            } else {
                binding.tvMissionIngCount.isVisible = false
            }
        }

        viewModel.postList.observe(viewLifecycleOwner) {
            postRecyclerViewAdapter.submitList(it)
            viewModel.setUnLoading()
        }

        viewModel.missionList.observe(viewLifecycleOwner) {
            accessGroupMissionRecyclerViewAdapter.submitList(it)
            binding.tvMissionIngCount.text = it.size.toString()
            viewModel.setUnLoading()
        }
    }

    override fun initAfterBinding() {
        val groupImage = requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_IMAGE)
        if (groupImage != "") {
            Glide.with(binding.ivGroupImage.context)
                .load(groupImage)
                .into(binding.ivGroupImage)
        }

        binding.tvGroupName.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NAME)
        binding.tvGroupMemberNumber.text =
            requireArguments().getString(GroupListRecyclerViewAdapter.GROUP_NUMBER)

        binding.ivSearch.setOnClickListener {
            Intent(requireContext(), SearchPostActivity::class.java).apply {
                putExtra(HomeFragment.GROUP_ALL_LIST, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }

        binding.ivBookmark.setOnClickListener {
            viewModel.setBookmark()
        }

        binding.layerGroupName.setOnClickListener {
            Intent(requireContext(), GroupInformationActivity::class.java).apply {
                putExtra(GroupListRecyclerViewAdapter.GROUP_Id, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }
    }

    private fun getGradePopUp(view: View, position: Int, contentId: Int) {
        // 팝업 생성
        val popupWindow = PopupWindow(
            activityPopupWindowBinding!!.root,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        // 현재 유지시킬 뷰 설정(이 줄을 없애면 키보드가 올라옴)
        popupWindow.contentView = activityPopupWindowBinding!!.root

        // 어떤 레이아웃 밑에 팝업을 달건지 설정
        popupWindow.showAsDropDown(view, 50, -250)

        activityPopupWindowBinding!!.ivEmotionOne.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type1.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type2.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type3.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type4.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type5.emotionPosition)
            )
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(
                contentId,
                position,
                RequestEmotionStatus(PopupWindowType.Type6.emotionPosition)
            )
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(contentId: Int, position: Int, emotionStatus: RequestEmotionStatus) {
        viewModel.setLoading()
        viewModel.setUpdateEmotion(contentId, position, emotionStatus)
    }
}