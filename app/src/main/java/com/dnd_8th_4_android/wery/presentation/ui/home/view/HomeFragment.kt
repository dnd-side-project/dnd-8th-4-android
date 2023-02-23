package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.view.WritingActivity
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialogDialog
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private var isSelectGroupId = 0

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)
        binding.vm = homeViewModel

        homeViewModel.getSignGroup()

        // 그룹 리스트
        groupRecyclerViewAdapter = GroupRecyclerViewAdapter(
            binding.activityGroup.ivAllGroup,
            binding.activityGroup.tvAllGroup
        )
        groupRecyclerViewAdapter.setGroupPostCallListener { groupId ->
            isSelectGroupId = groupId
            homeViewModel.getGroupPost(groupId.toString(), 1)
        }
        binding.activityGroup.rvMyGroup.adapter = groupRecyclerViewAdapter

        // 그룹 게시글
        postRecyclerViewAdapter = PostRecyclerViewAdapter()
        binding.activityGroup.rvMyGroupPost.adapter = postRecyclerViewAdapter
        binding.activityGroup.rvMyGroupPost.itemAnimator = null

        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener {
                val bottomSheet = PopupBottomDialogDialog()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { view, contentId ->
                getGradePopUp(view, contentId)
            }
        }

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
            if (it) showLoadingDialog(requireContext())
            else dismissLoadingDialog()
        }

        // 그룹 리스트가 바뀌는 경우) 1. 스와이프 2. 홈 탭
        homeViewModel.groupList.observe(viewLifecycleOwner) {
            initSelectedGroup()
            groupRecyclerViewAdapter.submitList(homeViewModel.groupList.value)
        }

        homeViewModel.postList.observe(viewLifecycleOwner) {
            postRecyclerViewAdapter.submitList(homeViewModel.postList.value)
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

        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                binding.etSearch.hideKeyboard()
                binding.etSearch.clearFocus()

                // 검색 동작
                homeViewModel.setLoading()
                homeViewModel.pageNumber = 1
                homeViewModel.searchWord = searchKeyword
                homeViewModel.groupPostSearch(isSelectGroupId, homeViewModel.searchWord, homeViewModel.pageNumber)
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.etSearch.showKeyboard()
        }

        binding.activityGroup.ivAllGroup.setOnClickListener {
            if (groupRecyclerViewAdapter.selectedItemImage != binding.activityGroup.ivAllGroup) {
                initSelectedGroup()
                homeViewModel.getGroupPost(homeViewModel.groupAllIdList.joinToString(), 1)
            }
        }

        binding.activityGroup.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, _, _, _ ->
            if (homeViewModel.isLoading.value == false && !homeViewModel.isNoData && !v.canScrollVertically(1)) {
                homeViewModel.pageNumber += 1
                if (homeViewModel.isSearchOn) {
                    homeViewModel.groupPostSearch(isSelectGroupId, homeViewModel.searchWord, homeViewModel.pageNumber)
                } else {
                    homeViewModel.getGroupPost(isSelectGroupId.toString(), homeViewModel.pageNumber)
                }
            }
        })

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), WritingActivity::class.java))
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

        isSelectGroupId = 0
    }

    private fun getGradePopUp(view: View, contentId: Int) {
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
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type1.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type2.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type3.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type4.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type5.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(contentId, RequestEmotionStatus(PopupWindowType.Type6.emotionPosition))
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(contentId: Int, emotionStatus: RequestEmotionStatus) {
        homeViewModel.setLoading()
        homeViewModel.setUpdateEmotion(isSelectGroupId, contentId, emotionStatus)
    }
}