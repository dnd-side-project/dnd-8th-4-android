package com.dnd_8th_4_android.wery.presentation.ui.search.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    override fun onRestart() {
        super.onRestart()
        viewModel.getSearchPost(groupAllList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initDataBinding() {
        viewModel.searchPostList.observe(this) {
            postSearchAdapter.submitList(it.toMutableList())
            binding.tvPostCount.text = it.size.toString()
        }
    }

    private fun initStartView() {
        groupAllList = intent.getStringExtra(HomeFragment.GROUP_ALL_LIST)!!

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.etSearch.requestFocus()
        Handler(Looper.getMainLooper())
            .postDelayed({
                binding.etSearch.showKeyboard()
            }, 400)

        binding.etSearch.doBeforeTextChanged { _, _, _, after ->
            binding.ivSearchClose.isVisible = after > 0
        }

        binding.etSearch.setOnEditorActionListener { textView, actionId, _ ->
            viewModel.searchKeyword.value = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && viewModel.searchKeyword.value != "") {
                if (!binding.layoutSearch.isVisible) {
                    binding.layoutSearch.isVisible = true
                    binding.ivSearch.isVisible = false
                }
                postSearchAdapter = PostSearchAdapter()
                binding.rvSearch.adapter = postSearchAdapter
                binding.rvSearch.itemAnimator = null

                viewModel.getSearchPost(groupAllList)
                postSearchAdapter.wordText = viewModel.searchKeyword.value!!
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