package com.dnd_8th_4_android.wery.presentation.ui.write.view

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityWritingBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.adapter.UploadPhotoAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.viewmodel.WritingViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil

class WritingActivity : BaseActivity<ActivityWritingBinding>(R.layout.activity_writing) {

    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter
    private val writingViewModel: WritingViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // 권한 요청 성공 -> 갤러리 열기
                Toast.makeText(this, "requestPermissonLauncher:갤러리 열기ㅋㅋ", Toast.LENGTH_SHORT)
                    .show()
            } else checkRequestPermission()
        }

    // 권한 체크 함수
    private fun checkRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "requestPermissonLauncher:갤러리 열기 ㅎㅎ", Toast.LENGTH_SHORT).show()
        } else permissionDialog()

    }

    // 권한 요청
    private fun permissionDialog() {
        fun doPositiveClick() {
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
            )
        }

        val dialog = DialogFragmentUtil(
            DialogInfo(
                "갤러리 접근 권한",
                "갤러리 접근 권한이 필요합니다.\n확인을 누르면 설정화면으로 이동합니다.",
                "닫기",
                "확인"
            )
        ) { doPositiveClick() }
        dialog.show(supportFragmentManager, dialog.tag)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.viewModel = writingViewModel
        setRvAdapter()
    }

    private fun initDataBinding() {
        setPhotoCnt()
    }

    private fun initAfterBinding() {
        setPhotoAddListener()
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

    private fun setPhotoAddListener() {
        binding.photoCardView.setOnClickListener {
            requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
        }
    }
}