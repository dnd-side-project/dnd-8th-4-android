package com.dnd_8th_4_android.wery.presentation.ui.search.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.search.adapter.PostSearchAdapter
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
import com.dnd_8th_4_android.wery.presentation.util.showKeyboard

class SearchPostActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private lateinit var postSearchAdapter: PostSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
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