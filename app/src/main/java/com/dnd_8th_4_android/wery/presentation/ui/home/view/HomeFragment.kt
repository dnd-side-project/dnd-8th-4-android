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
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ActivityPopupWindowBinding
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.view.WritingActivity
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration
import com.dnd_8th_4_android.wery.presentation.util.PopupBottomDialogDialog
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()
    private var activityPopupWindowBinding: ActivityPopupWindowBinding? = null

    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private lateinit var groupList: MutableList<ResponseGroupData.Data>
    private lateinit var postList: MutableList<ResponsePostData.Data>

    override fun initStartView() {
        activityPopupWindowBinding = ActivityPopupWindowBinding.inflate(layoutInflater)
        binding.vm = homeViewModel
    }

    override fun initDataBinding() {
        homeViewModel.isExistGroup.observe(viewLifecycleOwner) { isExistGroup ->
            if (isExistGroup) {
                makeList()

                binding.activityGroup.layoutAllGroup.isSelected = true
                groupRecyclerViewAdapter =
                    GroupRecyclerViewAdapter(groupList, binding.activityGroup.layoutAllGroup)
                binding.activityGroup.rvMyGroup.apply {
                    adapter = groupRecyclerViewAdapter
                    addItemDecoration(
                        MarginItemDecoration(
                            resources.getDimension(R.dimen.groupList_item_margin).toInt()
                        )
                    )
                }

                postRecyclerViewAdapter = PostRecyclerViewAdapter()
                postRecyclerViewAdapter.submitList(postList)
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
                            groupRecyclerViewAdapter.submitList(groupList.toMutableList())
                            postRecyclerViewAdapter.submitList(postList.toMutableList())
                        }, 1000)
                }
            } else {
                // TODO 그룹이 없는 경우
            }
        }

        homeViewModel.isUpdateList.observe(viewLifecycleOwner) {
            postList.clear()
            postList = it
            postRecyclerViewAdapter.submitList(postList.toMutableList())
        }
    }

    override fun initAfterBinding() {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    binding.activityGroup.layoutSwipeRefresh.isRefreshing = true
                    binding.activityGroup.scrollView.fullScroll(ScrollView.FOCUS_UP)
                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            binding.activityGroup.layoutSwipeRefresh.isRefreshing = false
                            groupRecyclerViewAdapter.submitList(groupList.toMutableList())
                            postRecyclerViewAdapter.submitList(postList)
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
                // TODO 검색 동작
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.etSearch.showKeyboard()
        }

        binding.activityGroup.layoutAllGroup.setOnClickListener {
            if (groupRecyclerViewAdapter.selectedItem != binding.activityGroup.layoutAllGroup) {
                binding.activityGroup.layoutAllGroup.isSelected =
                    !binding.activityGroup.layoutAllGroup.isSelected
                groupRecyclerViewAdapter.apply {
                    selectedItem.isSelected = false
                    selectedItem = binding.activityGroup.layoutAllGroup
                }
            }
            // TODO 전체보기 피드 호출
        }

        binding.btnFloatingAction.setOnClickListener {
            startActivity(Intent(requireContext(), WritingActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activityPopupWindowBinding = null
    }

    private fun makeList() {
        groupList = mutableListOf(
            ResponseGroupData.Data(1, R.drawable.img_no_group, "안녕하세요"),
            ResponseGroupData.Data(2, R.drawable.img_no_group, "DND활동중입니다"),
            ResponseGroupData.Data(3, R.drawable.img_no_group, "저희는8조입니다"),
            ResponseGroupData.Data(4, R.drawable.img_no_group, "Group111111")
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
                0
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
                0
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
                0
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
                0
            ),
        )
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
        homeViewModel.setUpdateList(position, emotionPosition, postList)
    }
}