package com.dnd_8th_4_android.wery.presentation.ui.search.view

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.home.view.HomeFragment
import com.dnd_8th_4_android.wery.presentation.ui.search.adapter.PostSearchAdapter
import com.dnd_8th_4_android.wery.presentation.ui.search.viewmodel.SearchPostViewModel
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPostActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel: SearchPostViewModel by viewModels()
    private lateinit var postSearchAdapter: PostSearchAdapter
    private var groupAllList = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {
        viewModel.searchPostList.observe(this) {
            postSearchAdapter.submitList(it)
        }
    }

    private fun initStartView() {
        groupAllList = intent.getStringExtra(HomeFragment.GROUP_ALL_LIST)!!

        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                if (!binding.layoutSearch.isVisible) {
                    binding.layoutSearch.isVisible = true
                    binding.ivSearch.isVisible = false

                    postSearchAdapter = PostSearchAdapter()
                    binding.rvSearch.adapter = postSearchAdapter
                    binding.rvSearch.itemAnimator = null
                }
                viewModel.getSearchPost(groupAllList, searchKeyword)
                binding.etSearch.hideKeyboard()
                binding.etSearch.clearFocus()
            }
            false
        }

        binding.ivSearchClose.setOnClickListener {
            binding.etSearch.text.clear()
            binding.etSearch.requestFocus()
            binding.etSearch.showKeyboard()
        }
    }
}