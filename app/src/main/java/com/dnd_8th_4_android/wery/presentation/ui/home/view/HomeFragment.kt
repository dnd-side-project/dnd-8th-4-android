package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.view.WritingActivity
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialogDialog
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)
        binding.vm = homeViewModel

        homeViewModel.getSignGroup()
    }

    override fun initDataBinding() {
        homeViewModel.groupList.observe(viewLifecycleOwner) {
            binding.activityGroup.ivAllGroup.isSelected = true
            binding.activityGroup.tvAllGroup.setTextAppearance(R.style.TextView_Title_12_Sb)

            groupRecyclerViewAdapter =
                GroupRecyclerViewAdapter(
                    homeViewModel.groupList.value!!,
                    binding.activityGroup.ivAllGroup,
                    binding.activityGroup.tvAllGroup
                )

            groupRecyclerViewAdapter.setGroupPostCallListener { groupId ->
                homeViewModel.getAllGroupPost(groupId.toString(), 1)
            }

            binding.activityGroup.rvMyGroup.apply {
                adapter = groupRecyclerViewAdapter
                addItemDecoration(
                    MarginItemDecoration(
                        resources.getDimension(R.dimen.groupList_item_margin).toInt()
                    )
                )
            }
        }

        homeViewModel.postList.observe(viewLifecycleOwner) {
            postRecyclerViewAdapter = PostRecyclerViewAdapter()
            postRecyclerViewAdapter.submitList(homeViewModel.postList.value)
            binding.activityGroup.rvMyGroupPost.adapter = postRecyclerViewAdapter
            binding.activityGroup.rvMyGroupPost.itemAnimator = null

            postRecyclerViewAdapter.apply {
                setPopupBottomClickListener {
                    val bottomSheet = PopupBottomDialogDialog()
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                }

                setPopupWindowClickListener { view, position ->
                    getGradePopUp(view, position)
                }
            }

            binding.activityGroup.layoutSwipeRefresh.setOnRefreshListener {
                Handler(Looper.getMainLooper())
                    .postDelayed({
                        binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                        groupRecyclerViewAdapter.submitList(homeViewModel.groupList.value!!.toMutableList())
                        postRecyclerViewAdapter.submitList(homeViewModel.postList.value!!.toMutableList())
                    }, 1000)
            }
        }

        homeViewModel.isNoAccess.observe(viewLifecycleOwner) {
            requireActivity().finish()
            startActivity(Intent(requireActivity(), SignActivity::class.java))
        }

        homeViewModel.isUpdateList.observe(viewLifecycleOwner) {
            homeViewModel.postList.value!!.clear()
            homeViewModel.setPostList(it)
            postRecyclerViewAdapter.submitList(homeViewModel.postList.value!!.toMutableList())
        }
    }

    override fun initAfterBinding() {
//        val bottomNavigationView =
//            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        bottomNavigationView.setOnItemReselectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.homeFragment -> {
//                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = true
//                    binding.activityGroup.scrollView.fullScroll(ScrollView.FOCUS_UP)
//                    Handler(Looper.getMainLooper())
//                        .postDelayed({
//                            binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
//                            groupRecyclerViewAdapter.submitList(homeViewModel.groupList.toMutableList())
//                            postRecyclerViewAdapter.submitList(homeViewModel.postList)
//                        }, 1000)
//                }
//            }
//        }

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

        binding.activityGroup.ivAllGroup.setOnClickListener {
            if (groupRecyclerViewAdapter.selectedItemImage != binding.activityGroup.ivAllGroup) {
                with(groupRecyclerViewAdapter) {
                    selectedItemImage.isSelected = false
                    selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)
                    selectedItemImage = binding.activityGroup.ivAllGroup
                    selectedItemText = binding.activityGroup.tvAllGroup
                }

                binding.activityGroup.ivAllGroup.isSelected =
                    !binding.activityGroup.ivAllGroup.isSelected
                binding.activityGroup.tvAllGroup.setTextAppearance(R.style.TextView_Title_12_Sb)

                homeViewModel.getAllGroupPost(homeViewModel.groupIdList.joinToString(), 1)
            }
        }

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), WritingActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityPopupWindowBinding = null
    }

    private fun getGradePopUp(view: View, position: Int) {
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
            setEmotion(position, PopupWindowType.Type1.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(position, PopupWindowType.Type2.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(position, PopupWindowType.Type3.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(position, PopupWindowType.Type4.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(position, PopupWindowType.Type5.emotionPosition)
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(position, PopupWindowType.Type6.emotionPosition)
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(position: Int, emotionPosition: Int) {
        homeViewModel.setUpdateList(position, emotionPosition, homeViewModel.postList.value!!)
    }
}