package com.dnd_8th_4_android.wery.presentation.ui.post.place.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace.Document
import com.dnd_8th_4_android.wery.databinding.ActivitySearchPlaceBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.place.adapter.SearchAdapter
import com.dnd_8th_4_android.wery.presentation.ui.post.place.viewmodel.SearchPlaceViewModel
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.view.UploadPostActivity
import com.dnd_8th_4_android.wery.presentation.util.hideKeyboard
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
        if (intent.hasExtra("fromMapSearch")) binding.tvSelectGroup.text = "장소 검색하기"
        searchAdapter = SearchAdapter { data -> getSearchResult(data) }
        binding.rvSearchResult.adapter = searchAdapter
    }

    private fun initDataBinding() {
        binding.etvSearch.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                searchPlaceViewModel.getSearchPlace(
                    binding.etvSearch.text.toString()
                )
                binding.etvSearch.hideKeyboard()
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

        /** [모아보기] 글쓰기 버튼에서 이동 했을 시
         *  닫았을 때 장소와 x,y 값을 전달한 후 글쓰기 화면으로 이동해준다.
         * */
        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    private fun showSearchResult() {
        searchPlaceViewModel.searchPlace.observe(this) { result ->
            searchAdapter.submitList(result)
        }
    }

    private fun getSearchResult(data: Document) {
        val intent = Intent()
        intent.putExtra("selectedPlace", data.place_name)
        intent.putExtra("LocationAddress",data.road_address_name)
        intent.putExtra("selectedX",data.x)
        intent.putExtra("selectedY",data.y)

        if (intent.hasExtra("fromMapBtn")) {
            Intent(this,UploadPostActivity::class.java).apply {
                putExtra("selectedPlace",data.place_name)
                putExtra("LocationAddress",data.road_address_name)
                putExtra("selectedX",data.x)
                putExtra("selectedY",data.y)
                putExtra("fromMapBtn",true)
                startActivity(this)
            }
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}