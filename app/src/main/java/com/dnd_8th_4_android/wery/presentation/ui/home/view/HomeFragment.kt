package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.alert.view.AlertPopupActivity
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import com.dnd_8th_4_android.wery.presentation.ui.search.view.SearchPostActivity
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignActivity
import com.dnd_8th_4_android.wery.presentation.util.PostPopupBottomDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    companion object {
        const val GROUP_ALL_LIST = "group_all_list"
    }

    override fun onResume() {
        super.onResume()
        binding.vm = homeViewModel

        homeViewModel.getSignGroup()
    }

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        // 그룹 리스트
        groupRecyclerViewAdapter = GroupRecyclerViewAdapter(
            binding.activityGroup.ivAllGroup,
            binding.activityGroup.tvAllGroup
        )
        groupRecyclerViewAdapter.setGroupPostCallListener { groupId ->
            homeViewModel.isSelectGroupId.value = groupId
            homeViewModel.getGroupPost()
        }
        binding.activityGroup.rvMyGroup.adapter = groupRecyclerViewAdapter
        binding.activityGroup.rvMyGroup.itemAnimator = null

        // 그룹 게시글
        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        binding.activityGroup.rvMyGroupPost.apply {
            adapter = postRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }

        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener { contentId, postMine, isSelected ->
                val bottomSheet = PostPopupBottomDialog(contentId, postMine, isSelected)
                bottomSheet.setOnBookmarkListener {
                    homeViewModel.getGroupPost()
                }
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { view, position, contentId ->
                getGradePopUp(view, position, contentId)
            }
        }

        // TODO
        binding.activityGroup.layoutSwipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper())
                .postDelayed({
                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                    homeViewModel.getSignGroup()
                }, 1000)
        }
    }

    override fun initDataBinding() {
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        homeViewModel.isExistGroup.observe(viewLifecycleOwner) {
            binding.activityNoGroup.isVisible = !it
        }

        // 그룹 리스트가 바뀌는 경우) 1. 스와이프 2. 홈 탭
        homeViewModel.groupList.observe(viewLifecycleOwner) {
            initSelectedGroup()
            groupRecyclerViewAdapter.submitList(it.toMutableList())
        }

        homeViewModel.postList.observe(viewLifecycleOwner) {
            if (it.content.isNotEmpty()) {
                binding.activityGroup.layoutNoPost.isVisible = false
                binding.activityGroup.layoutFinalPost.isVisible = true
            } else {
                binding.activityGroup.layoutNoPost.isVisible = true
                binding.activityGroup.layoutFinalPost.isVisible = false
            }
            postRecyclerViewAdapter.submitList(it.content.toMutableList())
        }

        homeViewModel.isNoAccess.observe(viewLifecycleOwner) {
            requireActivity().finish()
            startActivity(Intent(requireActivity(), SignActivity::class.java))
        }
    }

    override fun initAfterBinding() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                // TODO 보류
                R.id.homeFragment -> {
                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = true
                    binding.activityGroup.scrollView.fullScroll(ScrollView.FOCUS_UP)
                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                            homeViewModel.getSignGroup()
                        }, 1000)
                }
            }
        }

        binding.tvSearch.setOnClickListener {
            Intent(requireContext(), SearchPostActivity::class.java).apply {
                putExtra(GROUP_ALL_LIST, homeViewModel.groupAllIdList.joinToString())
                startActivity(this)
            }
        }

        binding.activityGroup.ivAllGroup.setOnClickListener {
            if (groupRecyclerViewAdapter.selectedItemImage != binding.activityGroup.ivAllGroup) {
                initSelectedGroup()
                homeViewModel.isSelectGroupId.value = -1
                homeViewModel.getGroupPost()
            }
        }

        binding.ivNotification.setOnClickListener {
            startActivity(Intent(requireContext(), AlertPopupActivity::class.java))
        }

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), UploadPostActivity::class.java))
        }

        binding.btnGroupCreate.setOnClickListener {
            startActivity(Intent(requireContext(), CreateGroupActivity::class.java))
        }

        binding.btnGroupInvite.setOnClickListener {
            startActivity(Intent(requireContext(), AlertPopupActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityPopupWindowBinding = null
    }

    private fun initSelectedGroup() {
        with(groupRecyclerViewAdapter) {
            selectedItemImage.isSelected = false
            selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)
            selectedItemImage = binding.activityGroup.ivAllGroup
            selectedItemText = binding.activityGroup.tvAllGroup
        }

        binding.activityGroup.ivAllGroup.isSelected =
            !binding.activityGroup.ivAllGroup.isSelected
        binding.activityGroup.tvAllGroup.setTextAppearance(R.style.TextView_Title_12_Sb)
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
        homeViewModel.setUpdateEmotion(contentId, position, emotionStatus)
    }
}