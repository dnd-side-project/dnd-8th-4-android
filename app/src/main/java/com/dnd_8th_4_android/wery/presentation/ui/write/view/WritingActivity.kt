package com.dnd_8th_4_android.wery.presentation.ui.write.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityWritingBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.view.adapter.UploadPhotoAdapter

class WritingActivity : BaseActivity<ActivityWritingBinding>(R.layout.activity_writing) {

    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        uploadPhotoAdapter = UploadPhotoAdapter()
        val itemList =
            mutableListOf<String>(
                "https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg",
                "https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg"
            )
        binding.rvPhoto.adapter = uploadPhotoAdapter
        uploadPhotoAdapter.submitList(itemList)
    }
}