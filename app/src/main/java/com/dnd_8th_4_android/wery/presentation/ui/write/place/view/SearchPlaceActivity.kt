package com.dnd_8th_4_android.wery.presentation.ui.write.place.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchPlaceBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.place.view.adapter.SearchAdapter

class SearchPlaceActivity :
    BaseActivity<ActivitySearchPlaceBinding>(R.layout.activity_search_place) {

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initAfterBinding()
    }

    private fun initStartView() {
        searchAdapter = SearchAdapter()
    }

    private fun initDataBinding() {
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
}