package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityProfileChangeBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_IMAGE
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_NICKNAME
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.ProfileChangeViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import com.dnd_8th_4_android.wery.presentation.util.MultiPartFileUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class ProfileChangeActivity :
    BaseActivity<ActivityProfileChangeBinding>(R.layout.activity_profile_change) {
    private val viewModel: ProfileChangeViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showSelectImgBottomSheet()
            } else checkRequestPermission()
        }

    private val requestPhotoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val clipData = it?.data?.clipData

                if (clipData == null) {
                    Glide.with(this).load(it?.data?.data!!).into(binding.ivImage)
                    viewModel.setImageUri(it.data?.data!!)
                } else {
                    Glide.with(this).load(clipData.getItemAt(0).uri).into(binding.ivImage)
                    viewModel.setImageUri(clipData.getItemAt(0).uri)
                }
                binding.tvSuccess.setTextColor(ContextCompat.getColor(this, R.color.black))
                viewModel.setEnabled()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.vm = viewModel

        binding.ivImage.clipToOutline = true
        Glide.with(this).load(intent.getStringExtra(USER_IMAGE))
            .into(binding.ivImage)

        binding.etNickname.setText(intent.getStringExtra(USER_NICKNAME))

        viewModel.setHttpUrl(intent.getStringExtra(USER_IMAGE) ?: "")
        viewModel.setTextCount(intent.getStringExtra(USER_NICKNAME)!!.length)
    }

    private fun initDataBinding() {
        viewModel.textCount.observe(this) {
            if (it in 0..14) {
                binding.tvCount.setTextColor(ContextCompat.getColor(this, R.color.gray600))
                binding.etNickname.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_et_my_page_change)
            } else {
                binding.tvCount.setTextColor(ContextCompat.getColor(this, R.color.color_eb0555))
                binding.etNickname.background =
                    ContextCompat.getDrawable(this, R.drawable.shape_white_radius_8_eb0555)
                binding.tvNicknameError.isVisible = true
            }
        }

        viewModel.isSuccess.observe(this) { finish() }
    }

    private fun initAfterBinding() {
        binding.etNickname.setOnFocusChangeListener { _, gainFocus ->
            if (gainFocus) {
                binding.tvSuccess.setTextColor(ContextCompat.getColor(this, R.color.black))
                viewModel.setEnabled()
            }
        }

        binding.ivAlbumImage.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        binding.ivEdtDelete.setOnClickListener {
            binding.etNickname.text.clear()
        }

        binding.tvSuccess.setOnClickListener {
            val data = viewModel.setNickNameRequestBodyData(binding.etNickname.text.toString())
            val galleryUri = viewModel.groupImg.value
            val httpUri = viewModel.httpUri.value
            Thread {
                kotlin.run {
                    var image: MultipartBody.Part? = null
                    if (galleryUri != "".toUri()) {
                        image = MultiPartFileUtil(this, "file").uriToFile(galleryUri)
                    } else {
                        if (httpUri!!.contains("https")) image =
                            MultiPartFileUtil(this, "file").httpsToFile(httpUri)
                    }
                    viewModel.patchModifyGroup(data, image)
                }
            }.start()
        }

        binding.ivClose.setOnClickListener {
            val dialog = DialogFragmentUtil(
                DialogInfo(
                    "프로필 수정을 종료하시겠어요?",
                    "수정 종료 시 수정 중인 정보는 저장되지 않아요.",
                    "취소",
                    "수정 종료"
                )
            ) {
                finish()
                overridePendingTransition(0, 0)
            }
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    // 권한 체크 함수
    private fun checkRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            showSelectImgBottomSheet()
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

    private fun showSelectImgBottomSheet() {
        ProfileChangePopupBottomDialog({ openGallery() }, { removePhoto() }).show(
            supportFragmentManager,
            null
        )
    }

    private fun openGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_PICK
        }
        requestPhotoActivity.launch(intent)
    }

    private fun removePhoto() {
        Glide.with(this).load(R.drawable.img_group_default).into(binding.ivImage)
        viewModel.setImageUri("".toUri())
        viewModel.setHttpUrl("")
        binding.tvSuccess.setTextColor(ContextCompat.getColor(this, R.color.black))
        viewModel.setEnabled()
    }
}