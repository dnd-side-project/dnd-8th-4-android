package com.dnd_8th_4_android.wery.presentation.ui.detail.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailImageData
import com.dnd_8th_4_android.wery.databinding.ActivityPostDetailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailImageRecyclerViewAdapter

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>(R.layout.activity_post_detail) {
    private lateinit var imageList: ResponsePostDetailImageData.Data
    private lateinit var postDetailImageRecyclerViewAdapter: PostDetailImageRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        makeList()
        postDetailImageRecyclerViewAdapter = PostDetailImageRecyclerViewAdapter(imageList)
        binding.rvPostImage.adapter = postDetailImageRecyclerViewAdapter

    }

    private fun initDataBinding() {

    }

    private fun initAfterBinding() {

    }

    private fun makeList() {
        imageList =
            ResponsePostDetailImageData.Data(
                listOf(
                    R.drawable.img_no_group,
                    R.drawable.img_no_group,
                    R.drawable.img_no_group
                )
            )
    }
}