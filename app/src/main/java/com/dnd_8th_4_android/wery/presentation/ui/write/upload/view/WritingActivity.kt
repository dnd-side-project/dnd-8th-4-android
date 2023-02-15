package com.dnd_8th_4_android.wery.presentation.ui.write.upload.view

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityWritingBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.place.view.SearchPlaceActivity
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter.UploadPhotoAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel.WritingViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil

class WritingActivity : BaseActivity<ActivityWritingBinding>(R.layout.activity_writing) {

    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter
    private val writingViewModel: WritingViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else checkRequestPermission()
        }

    private val requestPhotoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val clipData = it?.data?.clipData
                val clipDataSize = clipData?.itemCount

                clipData?.let { _ ->
                    if (checkPhotoLimits(clipDataSize!!)) {
                        val currentList = uploadPhotoAdapter.currentList.toMutableList()
                        for (i in 0 until clipDataSize) { //선택 한 사진 수만큼 반복
                            val selectedImageUri = clipData.getItemAt(i).uri
                            currentList.add(selectedImageUri.toString())
                        }
                        uploadPhotoAdapter.submitList(currentList)
                    }
                }
            }
        }

    private val requestSearchActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedPlace = it.data?.getStringExtra("selectedPlace")
                    ?: getString(R.string.search_place_hint)
                writingViewModel.selectedPlace.value = selectedPlace
            }
        }

    private fun checkPhotoLimits(selectedPhotoCnt: Int): Boolean {
        val totalPhotoCnt = uploadPhotoAdapter.currentList.size + selectedPhotoCnt
        if (totalPhotoCnt > 5) {
            showToast("이미지는 최대 5장까지 등록 가능합니다.")
            return false
        }

        writingViewModel.setPhotoCnt(totalPhotoCnt)

        return true
    }

    // 권한 체크 함수
    private fun checkRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
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
        selectGroupListener()
        addMyPlace()
    }

    private fun setRvAdapter() {
        uploadPhotoAdapter = UploadPhotoAdapter { imgUrl -> onItemDelete(imgUrl) }
        binding.rvPhoto.adapter = uploadPhotoAdapter
    }

    private fun onItemDelete(imgUrl: String) {
        val currentList = uploadPhotoAdapter.currentList.toMutableList()
        currentList.remove(imgUrl)
        uploadPhotoAdapter.submitList(currentList)

        writingViewModel.setPhotoCnt(currentList.size)
    }

    private fun setPhotoCnt() {
        writingViewModel.photoCnt.observe(this) {
            binding.tvPhotoCnt.apply {
                if (it == 0) {
                    setTextAppearance(R.style.TextView_Body_12_R)
                    setTextColor(resources.getColor(R.color.gray800, null))
                } else {
                    setTextAppearance(R.style.TextView_Body_12_M)
                    setTextColor(resources.getColor(R.color.black, null))
                }
            }
        }
    }

    private fun setPhotoAddListener() {
        binding.photoCardView.setOnClickListener {
            requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_PICK
        }
        requestPhotoActivity.launch(intent)
    }

    private fun addMyPlace() {
        writingViewModel.selectedPlace.value = getString(R.string.search_place_hint)
        binding.layoutAddPlace.setOnClickListener {
            requestSearchActivity.launch(Intent(this, SearchPlaceActivity::class.java))
        }
    }

    private fun selectGroupListener() {
        writingViewModel.selectedGroup.value = getString(R.string.writing_select_group)
        binding.layoutSelectGroup.setOnClickListener {
            writingViewModel.selectedGroupState.value = true
            SelectGroupBottomDialog(writingViewModel).show(supportFragmentManager, null)
        }
    }

}