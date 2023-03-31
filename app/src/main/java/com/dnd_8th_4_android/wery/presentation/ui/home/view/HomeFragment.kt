package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.alert.view.AlertPopupActivity
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.HomeRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageBookmarkActivity
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
    private lateinit var postRecyclerViewAdapter: HomeRecyclerViewAdapter

    companion object {
        const val GROUP_ALL_LIST = "group_all_list"
    }

    private val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.getBooleanExtra("isModifySuccess", false).let { isModifySuccess ->
            if (isModifySuccess == true) {
                homeViewModel.pageNumber.value = 1
                homeViewModel.isSelectedEmotion.value = false
                homeViewModel.getGroupPost()
                binding.activityGroup.rvHome.scrollToPosition(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityPopupWindowBinding = null
    }

    override fun initStartView() {
        binding.vm = homeViewModel
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)

        homeViewModel.isSelectedEmotion.value = false
        homeViewModel.pageNumber.value = 1

        // 처음에 무조건 실행시켜줄 수 밖에 없을듯
        homeViewModel.isSelectGroupId.value = -1
        homeViewModel.getSignGroup()

        // 그룹 게시글 : 문제점) adapter 초기화 할때 postList = 0 자동 입력됌
        postRecyclerViewAdapter =
            HomeRecyclerViewAdapter(requireContext(), homeViewModel, viewLifecycleOwner)
        binding.activityGroup.rvHome.apply {
            adapter = postRecyclerViewAdapter
            itemAnimator = null
        }

        postRecyclerViewAdapter.apply {
            setPopupBottomClickListener { position, contentId, postMine, isSelected ->
                val bottomSheet = PostPopupBottomDialog(contentId, postMine, isSelected)
                homeViewModel.adapterPosition.value = position
                bottomSheet.setOnBookmarkListener {
                    homeViewModel.pageNumber.value = homeViewModel.adapterPosition.value!! / 10 + 1
                    homeViewModel.isSelectedEmotion.value = true
                    homeViewModel.getGroupPost()
                }
                bottomSheet.setOnModifyListener {
                    if (it) {
                        homeViewModel.pageNumber.value =
                            homeViewModel.adapterPosition.value!! / 10 + 1
                        homeViewModel.isSelectedEmotion.value = true
                        homeViewModel.getGroupPost()
                    }
                }

                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }

            setPopupWindowClickListener { position, view, contentId ->
                getGradePopUp(position, view, contentId)
            }
        }

        binding.activityGroup.layoutSwipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper())
                .postDelayed({
                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                    homeViewModel.isSelectedEmotion.value = false
                    homeViewModel.pageNumber.value = 1
                    homeViewModel.getSignGroup()
                }, 1000)
        }

        binding.ivBookmark.setOnClickListener {
            startActivity(Intent(requireContext(), MyPageBookmarkActivity::class.java))
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

        homeViewModel.isNewNotification.observe(viewLifecycleOwner) {
            binding.ivNotificationAlert.isVisible = !it
        }

        homeViewModel.postList.observe(viewLifecycleOwner) {
            // 첫 실행, 스크롤, 게시글 등록
            if (homeViewModel.isSelectedEmotion.value == false) {
                if (homeViewModel.pageNumber.value == 1) {
                    Log.e("태그", it.content.toString())
                    postRecyclerViewAdapter.submitList(it.content)
                } else {
                    postRecyclerViewAdapter.insertList(it.content, homeViewModel.pageNumber.value!!)
                }
            } else { // 감정 이모지 등록, 게시글 수정
                val currentList = postRecyclerViewAdapter.postList

                val calculatePosition = if (homeViewModel.adapterPosition.value != 10) {
                    homeViewModel.adapterPosition.value!! % 10 - 1
                } else {
                    9
                }

                currentList.forEach { content ->
                    if (content.id == it.content[calculatePosition].id) {
                        currentList[homeViewModel.adapterPosition.value!! - 1] =
                            it.content[calculatePosition]

                        postRecyclerViewAdapter.notifyItemChanged(homeViewModel.adapterPosition.value!!)
                        return@observe
                    }
                }
            }
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
                R.id.homeFragment -> {
                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = true
                    binding.activityGroup.rvHome.scrollToPosition(0)
                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                            homeViewModel.isSelectGroupId.value = -1
                            homeViewModel.isSelectedEmotion.value = false
                            homeViewModel.pageNumber.value = 1
                            homeViewModel.getSignGroup()
                        }, 1000)
                }
            }
        }

        binding.tvSearch.setOnClickListener {
            if (homeViewModel.groupAllIdList.isNotEmpty()) {
                Intent(requireContext(), SearchPostActivity::class.java).apply {
                    putExtra(GROUP_ALL_LIST, homeViewModel.groupAllIdList.joinToString())
                    startActivity(this)
                }
            } else {
                Toast.makeText(requireContext(), "가입된 그룹이 없어요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.activityGroup.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if (!homeViewModel.isLoading.value!!
                    && postRecyclerViewAdapter.postList.size != 0
                    && postRecyclerViewAdapter.postList.size % 10 == 0
                    && lastVisibleItemPosition == postRecyclerViewAdapter.itemCount - 1
                ) {
                    homeViewModel.setLoading()
                    homeViewModel.isSelectedEmotion.value = false
                    homeViewModel.setUpPageNumber()
                    homeViewModel.getGroupPost()
                }
            }
        })

        binding.ivNotification.setOnClickListener {
            startActivity(Intent(requireContext(), AlertPopupActivity::class.java))
        }

        binding.btnFloatingAction.setOnClickListener {
            requestLauncher.launch(Intent(requireContext(), UploadPostActivity::class.java))
        }

        binding.btnGroupCreate.setOnClickListener {
            startActivity(Intent(requireContext(), CreateGroupActivity::class.java))
        }

        binding.btnGroupInvite.setOnClickListener {
            startActivity(Intent(requireContext(), AlertPopupActivity::class.java))
        }
    }

    private fun getGradePopUp(position: Int, view: View, contentId: Int) {
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

        homeViewModel.adapterPosition.value = position
        homeViewModel.contentId.value = contentId

        activityPopupWindowBinding!!.ivEmotionOne.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type1.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionTwo.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type2.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionThree.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type3.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFour.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type4.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionFive.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type5.emotionPosition))
            popupWindow.dismiss()
        }

        activityPopupWindowBinding!!.ivEmotionSix.setOnClickListener {
            setEmotion(RequestEmotionStatus(PopupWindowType.Type6.emotionPosition))
            popupWindow.dismiss()
        }
    }

    private fun setEmotion(emotionStatus: RequestEmotionStatus) {
        homeViewModel.pageNumber.value = homeViewModel.adapterPosition.value!! / 10 + 1
        homeViewModel.isSelectedEmotion.value = true
        homeViewModel.setUpdateEmotion(emotionStatus)
    }
}