package com.dnd_8th_4_android.wery.presentation.ui.write.place.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchPlaceBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.place.adapter.SearchAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.place.viewmodel.SearchPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPlaceActivity :
    BaseActivity<ActivitySearchPlaceBinding>(R.layout.activity_search_place) {

    private lateinit var searchAdapter: SearchAdapter
    private val searchPlaceViewModel: SearchPlaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        searchAdapter = SearchAdapter()
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun initDataBinding() {
        binding.etvSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                searchPlaceViewModel.getSearchPlace(
                    "KakaoAK 577bae1f4d5f3e349cb0b4c286bfa7a1",
                    binding.etvSearch.text.toString()
                )
                showSearchResult()
            }
            false
        }
    }

    private fun initAfterBinding() {
        binding.etvSearch.addTextChangedListener {
            if (it!!.isNotEmpty()) {
                binding.viewFocused.isSelected = true
                binding.ivSearchDelete.visibility = View.VISIBLE
            } else {
                binding.viewFocused.isSelected = false
                binding.ivSearchDelete.visibility = View.GONE
            }
        }
        binding.ivSearchDelete.setOnClickListener {
            binding.etvSearch.text.clear()
        }
    }

    private fun showSearchResult() {
        searchPlaceViewModel.searchPlace.observe(this) { result ->
            searchAdapter.submitList(result)
        }
    }
}