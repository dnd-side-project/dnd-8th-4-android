package com.dnd_8th_4_android.wery.presentation.ui.group.create.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateGroupBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.create.viewmodel.CreateGroupViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(R.layout.activity_create_group) {

    private val createGroupViewModel: CreateGroupViewModel by viewModels()

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
                if (clipData == null) {
                    val selectedImageUri = it?.data?.data!!
                    Glide.with(this).load(selectedImageUri).into(binding.ivGroupImg)
                    binding.frameLayoutImg.visibility = View.GONE
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.viewModel = createGroupViewModel
    }

    private fun initDataBinding() {
        setGroupNameUi()
        setGroupIntroUi()
    }

    // 권한 체크 함수
    private fun checkRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    private fun setGroupNameUi() {
        createGroupViewModel.groupNameTxt.observe(this) {
            binding.tvGroupNameLimit.text =
                getString(R.string.create_group_name_limit).format(it.length)
        }
    }

    private fun setGroupIntroUi() {
        createGroupViewModel.groupIntroduceTxt.observe(this) {
            binding.tvGroupIntroduceLimit.text =
                getString(R.string.create_group_introduce_limit).format(it.length)
        }
    }

    private fun initAfterBinding() {
        setTxtError(binding.etvGroupName, binding.tvGroupNameLimit, 12, binding.ivGroupNameClose)
        setTxtError(
            binding.etvGroupIntroduce,
            binding.tvGroupIntroduceLimit,
            30,
            binding.ivGroupIntroduceClose
        )

        setTxtCancelListener(binding.etvGroupName, binding.ivGroupNameClose)
        setTxtCancelListener(binding.etvGroupIntroduce, binding.ivGroupIntroduceClose)

        setGroupImg()

        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    private fun setTxtError(etv: EditText, tv: TextView, lenCnt: Int, ivClose: ImageView) {
        etv.addTextChangedListener {
            if (it?.length!! > lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_eb0555)
                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.color_eb0555, null)), 0, 2,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            } else {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_black)
                var end = 1
                end = if (it.length < 10) 1 else 2

                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.black, null)), 0, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
        }

        etv.setOnFocusChangeListener { v, hasFocus ->
            if (!v.hasFocus() && etv.text.length <= lenCnt) {
                etv.setBackgroundResource(R.drawable.shape_white_radius_8_gray300)
                var end = 1
                end = if (etv.text.length < 10) 1 else 2
                (tv.text as Spannable).setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.gray600, null)), 0, end,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }

            if (!v.hasFocus()) ivClose.visibility = View.GONE else ivClose.visibility = View.VISIBLE
        }
    }

    private fun setTxtCancelListener(etv: EditText, ivClose: ImageView) {
        etv.addTextChangedListener {
            if (it!!.isNotEmpty()) ivClose.visibility = View.VISIBLE
            else ivClose.visibility = View.GONE
        }
        ivClose.setOnClickListener {
            etv.text.clear()
        }
    }

    private fun setGroupImg() {
        binding.ivGroupImg.clipToOutline = true
        binding.frameLayoutImg.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        binding.ivGroupImg.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_PICK
        }
        requestPhotoActivity.launch(intent)
    }
}