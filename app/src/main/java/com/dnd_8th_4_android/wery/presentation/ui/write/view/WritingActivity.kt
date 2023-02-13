package com.dnd_8th_4_android.wery.presentation.ui.write.view

import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityWritingBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.adapter.UploadPhotoAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.viewmodel.WritingViewModel

class WritingActivity : BaseActivity<ActivityWritingBinding>(R.layout.activity_writing) {
    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter
    private val writingViewModel: WritingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
    }

    private fun initStartView() {
        binding.viewModel = writingViewModel
        setRvAdapter()
    }

    private fun initDataBinding() {
        setPhotoCnt()
    }

    private fun setRvAdapter() {
        uploadPhotoAdapter = UploadPhotoAdapter { imgUrl -> onItemDelete(imgUrl) }
        val itemList =
            mutableListOf<String>(
                "https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg",
                "https://blog.kakaocdn.net/dn/tEMUl/btrDc6957nj/NwJoDw0EOapJNDSNRNZK8K/img.jpg"
            )
        binding.rvPhoto.adapter = uploadPhotoAdapter
        uploadPhotoAdapter.submitList(itemList)
    }

    private fun onItemDelete(imgUrl: String) {
        val currentList = uploadPhotoAdapter.currentList.toMutableList()
        currentList.remove(imgUrl)
        uploadPhotoAdapter.submitList(currentList)
    }

    private fun setPhotoCnt() {
        writingViewModel.photoCnt.observe(this) {
            binding.tvPhotoCnt.apply {
                if (it == 0) {
                    setTextAppearance(R.style.TextView_Body_12_R)
                    setTextColor(resources.getColor(R.color.black, null))
                } else {
                    setTextAppearance(R.style.TextView_Body_12_M)
                    setTextColor(resources.getColor(R.color.gray800, null))
                }
            }
        }
    }
}