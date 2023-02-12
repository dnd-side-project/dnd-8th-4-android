package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.app.Activity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.PostRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel.HomeViewModel
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private lateinit var groupList: MutableList<ResponseGroupData.Data>
    private lateinit var postList: MutableList<ResponsePostData.Data>

    override fun initStartView() {
        binding.vm = homeViewModel
    }

    override fun initDataBinding() {
        homeViewModel.isExistGroup.observe(viewLifecycleOwner) { isExistGroup ->
            if (isExistGroup) {
                makeList()
                groupRecyclerViewAdapter = GroupRecyclerViewAdapter()
                groupRecyclerViewAdapter.submitList(groupList)
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
                postRecyclerViewAdapter.setItemClickListener {
                    val bottomSheet = PopupBottomDialogDialog()
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                }
            } else {

            }
        }
    }

    override fun initAfterBinding() {
        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                hideKeyboard(textView)
                binding.etSearch.clearFocus()
                // TODO 검색 동작
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            showKeyboard(binding.etSearch)
        }
    }

    private fun makeList() {
        groupList = mutableListOf(
            ResponseGroupData.Data("안녕하세요"),
            ResponseGroupData.Data("DND활동중입니다"),
            ResponseGroupData.Data("저희는8조입니다"),
            ResponseGroupData.Data("Group111111")
        )

        postList = mutableListOf(
            ResponsePostData.Data(
                "User1",
                "Group1",
                "111111111피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. ",
                listOf(R.drawable.bg_no_group, R.drawable.bg_crying_face),
                "1H:MM",
                "11"
            ),
            ResponsePostData.Data(
                "User2",
                "Group2",
                "222222222피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. ",
                listOf(R.drawable.bg_no_group, R.drawable.bg_crying_face),
                "2H:MM",
                "22"
            ),
            ResponsePostData.Data(
                "User3",
                "Group3",
                "33333333피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다.",
                listOf(R.drawable.bg_no_group, R.drawable.bg_crying_face),
                "3H:MM",
                "33"
            ),
            ResponsePostData.Data(
                "User4",
                "Group4",
                "4444444444피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다. 피드의 글 미리 보기는 네줄까지 보이고 이후는 점으로 대체됩니다.",
                listOf(R.drawable.bg_no_group, R.drawable.bg_crying_face),
                "4H:MM",
                "44"
            ),
        )
    }

    private fun hideKeyboard(textView: TextView) {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(textView.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        view.requestFocus()
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}