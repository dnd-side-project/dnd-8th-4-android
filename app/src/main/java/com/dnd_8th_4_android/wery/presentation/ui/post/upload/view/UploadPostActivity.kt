package com.dnd_8th_4_android.wery.presentation.ui.post.upload.view

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityUploadPostBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view.StickerAlertActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view.StickerDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view.StickerFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.view.MissionDetailActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.place.view.SearchPlaceActivity
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.adapter.UploadPhotoAdapter
import com.dnd_8th_4_android.wery.presentation.ui.post.upload.viewmodel.PostViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import com.dnd_8th_4_android.wery.presentation.util.MultiPartFileUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class UploadPostActivity : BaseActivity<ActivityUploadPostBinding>(R.layout.activity_upload_post) {

    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter
    private val postViewModel: PostViewModel by viewModels()

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
                val selectedX = it.data?.getDoubleExtra("selectedX", -1.0)
                val selectedY = it.data?.getDoubleExtra("selectedY", -1.0)

                postViewModel.selectedPlace.value = selectedPlace
                postViewModel.selectedLongitude.value = selectedX.toString()
                postViewModel.selectedLatitude.value = selectedY.toString()
            }
        }

    private fun checkPhotoLimits(selectedPhotoCnt: Int): Boolean {
        val totalPhotoCnt = uploadPhotoAdapter.currentList.size + selectedPhotoCnt
        if (totalPhotoCnt > 5) {
            showToast("이미지는 최대 5장까지 등록 가능합니다.")
            return false
        }

        postViewModel.setPhotoCnt(totalPhotoCnt)

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
        isFromModify()
        binding.viewModel = postViewModel
        setRvAdapter()
        selectGroupListener()

        val groupName = intent.getStringExtra(MissionDetailActivity.GROUP_NAME)
        val groupId = intent.getLongExtra(MissionDetailActivity.GROUP_ID, 0L)
        val placeName = intent.getStringExtra(MissionDetailActivity.PLACE_NAME)
        val latitude = intent.getDoubleExtra(MissionDetailActivity.LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(MissionDetailActivity.LONGITUDE, 0.0)

        if (intent.hasExtra(MissionDetailActivity.GROUP_NAME)) {
            binding.layoutSelectGroup.isEnabled = false
            postViewModel.selectedGroup.value = groupName
        }
        postViewModel.setGroupId(groupId)
        postViewModel.selectedLatitude.value = latitude.toString()
        postViewModel.selectedLongitude.value = longitude.toString()

        binding.tvAddPlace.text = placeName
    }

    private fun isFromModify() {
        if (intent.hasExtra("contentId")) {
            binding.layoutSelectGroup.isEnabled = false
            postViewModel.getExistingPostData(intent.getIntExtra("contentId", 0))
        }
    }

    private fun initDataBinding() {
        setPhotoCnt()
        setLoadingDialog()
        if (intent.hasExtra("contentId")) getExistingPhotoList()
        checkStickerAfterUploadMissionFeed()
    }

    private fun checkStickerAfterUploadMissionFeed() {
        postViewModel.missionStickerData.observe(this) {
            if (it.data.isGetNewSticker) {
                finish()
            } else {
                finish()
                Log.d("kite","에에에에엥?")
                Intent(this, StickerAlertActivity::class.java).apply {
                    putExtra(StickerAlertActivity.STICKER_GROUP_ID, it.data.getNewStickerGroupId)
                    startActivity(this)
                }
                /*StickerInfoBottomDialog("으흐흑", 2) {
                    moveToStickerDetail(1)
                }.show(
                    supportFragmentManager,
                    null
                )*/
            }
        }
    }

    private fun initAfterBinding() {
        setPhotoAddListener()
        registerListener()
        addPlaceListener()
        closeListener()
    }

    private fun setRvAdapter() {
        uploadPhotoAdapter = UploadPhotoAdapter { imgUrl -> onItemDelete(imgUrl) }
        binding.rvPhoto.adapter = uploadPhotoAdapter
    }

    private fun onItemDelete(imgUrl: String) {
        val currentList = uploadPhotoAdapter.currentList.toMutableList()
        currentList.remove(imgUrl)
        uploadPhotoAdapter.submitList(currentList)

        postViewModel.setPhotoCnt(currentList.size)
    }

    private fun getExistingPhotoList() {
        postViewModel.photoUrlList.observe(this) {
            val currentList = uploadPhotoAdapter.currentList.toMutableList()
            for (i in it.indices) {
                currentList.add(it[i].imageUrl)
            }
            uploadPhotoAdapter.submitList(currentList)
        }
    }

    private fun setLoadingDialog() {
        postViewModel.isLoading.observe(this) {
            if (it) showLoadingDialog()
            else {
                dismissLoadingDialog()
                finish()
            }
        }
    }

    private fun setPhotoCnt() {
        postViewModel.photoCnt.observe(this) {
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
            type = "image/jpeg"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_PICK
        }
        requestPhotoActivity.launch(intent)
    }

    private fun addPlaceListener() {
        if (intent?.hasExtra("placeName") == true) {
            postViewModel.selectedPlace.value = intent?.getStringExtra("placeName")
        } else {
            postViewModel.selectedPlace.value = getString(R.string.search_place_hint)
        }
        binding.layoutAddPlace.setOnClickListener {
            requestSearchActivity.launch(Intent(this, SearchPlaceActivity::class.java))
        }
    }

    private fun selectGroupListener() {
        postViewModel.selectedGroup.value = getString(R.string.writing_select_group)
        binding.layoutSelectGroup.setOnClickListener {
            postViewModel.selectedGroupState.value = true
            postViewModel.getGroupList()
            SelectGroupBottomDialog(postViewModel, "w").show(supportFragmentManager, null)
        }
    }

    private fun registerListener() {
        binding.tvRegister.setOnClickListener {
            if (intent.hasExtra("contentId")) modifyFeed()
            else if (intent.hasExtra("missionId")) uploadMissionFeed()
            else uploadFeed()
        }
    }

    private fun uploadFeed() {
        postViewModel.setLoadingState(true)
        val textHasMap = postViewModel.setUploadRequestBodyData(
            binding.etvNote.text.toString(),
            postViewModel.selectedLatitude.value!!,
            postViewModel.selectedLongitude.value!!,
            binding.tvAddPlace.text.toString(),
        )
        val imgFileList = mutableListOf<MultipartBody.Part>()
        for (imgUrl in uploadPhotoAdapter.currentList) {
            imgFileList.add(MultiPartFileUtil(this, "multipartFile").uriToFile(imgUrl.toUri()))
        }
        postViewModel.uploadFeed(postViewModel.groupId.value!!, textHasMap, imgFileList)
    }

    private fun modifyFeed() {
        postViewModel.setLoadingState(true)
        val textHasMap = postViewModel.setModifyRequestBodyData(
            binding.etvNote.text.toString(),
            intent.getIntExtra("contentId", -1).toLong().toString(),
            postViewModel.selectedLatitude.value!!,
            postViewModel.selectedLongitude.value!!,
            binding.tvAddPlace.text.toString(),
        )
        val imgFileList = mutableListOf<MultipartBody.Part>()
        Thread {
            kotlin.run {
                for (imgUrl in uploadPhotoAdapter.currentList) {
                    if (imgUrl.contains("https")) imgFileList.add(
                        MultiPartFileUtil(
                            this,
                            "multipartFile"
                        ).httpsToFile(imgUrl)!!
                    )
                    else imgFileList.add(
                        MultiPartFileUtil(
                            this,
                            "multipartFile"
                        ).uriToFile(imgUrl.toUri())
                    )
                }
            }
            postViewModel.modifyFeed(textHasMap, imgFileList)
        }.start()
    }

    private fun uploadMissionFeed() {
        val textHasMap = postViewModel.setUploadRequestBodyData(
            binding.etvNote.text.toString(),
            postViewModel.selectedLatitude.value!!,
            postViewModel.selectedLongitude.value!!,
            binding.tvAddPlace.text.toString(),
        )
        val imgFileList = mutableListOf<MultipartBody.Part>()
        for (imgUrl in uploadPhotoAdapter.currentList) {
            imgFileList.add(MultiPartFileUtil(this, "multipartFiles").uriToFile(imgUrl.toUri()))
        }
        postViewModel.uploadMissionFeed(intent.getIntExtra("missionId", 0), textHasMap, imgFileList)
    }

    private fun closeListener() {
        binding.ivClose.setOnClickListener {
            if (binding.tvRegister.isEnabled) {
                DialogFragmentUtil(
                    DialogInfo(
                        getString(R.string.writing_title_close),
                        getString(R.string.writing_text_close),
                        getString(R.string.writing_cancel),
                        getString(R.string.writing_close)
                    )
                ) { finish() }.show(supportFragmentManager, null)
            } else {
                finish()
            }
        }
    }
}