package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.app.Activity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.FragmentHomeBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.adapter.GroupRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.util.MarginItemDecoration

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    private lateinit var groupList: MutableList<ResponseGroupData.Data>

    override fun initStartView() {
        makeGroupList()
        groupRecyclerViewAdapter = GroupRecyclerViewAdapter()
        groupRecyclerViewAdapter.submitList(groupList)
        binding.activityGroup.rvMyGroup.apply {
            adapter = groupRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.groupList_item_margin).toInt()
                )
            )
        }
    }

    override fun initDataBinding() {}

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

    private fun makeGroupList() {
        groupList = mutableListOf(
            ResponseGroupData.Data("Group1"),
            ResponseGroupData.Data("Group2"),
            ResponseGroupData.Data("Group3"),
            ResponseGroupData.Data("Group4")
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