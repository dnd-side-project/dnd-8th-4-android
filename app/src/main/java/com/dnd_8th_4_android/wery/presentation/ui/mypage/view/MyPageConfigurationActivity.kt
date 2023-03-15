package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageConfigurationBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.MyPageConfigurationViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignActivity
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageConfigurationActivity :
    BaseActivity<ActivityMypageConfigurationBinding>(R.layout.activity_mypage_configuration) {
    private val viewModel: MyPageConfigurationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener { finish() }

        binding.tvLogout.setOnClickListener {
            val dialog = DialogFragmentUtil(
                DialogInfo(
                    "로그아웃",
                    "로그아웃 하시겠습니까?",
                    "취소",
                    "로그아웃"
                )
            ) { doLogoutPositiveClick() }
            dialog.show(this.supportFragmentManager, dialog.tag)
        }

        binding.tvSecession.setOnClickListener {
            val dialog = DialogFragmentUtil(
                DialogInfo(
                    "진짜로.. 탈퇴.... 하시겠어요?",
                    "탈퇴하면 계정 정보는 모두 삭제되지만, 그룹에 남긴 글은 유지됩니다. 삭제된 정보는 되돌릴 수 없습니다.",
                    "취소",
                    "탈퇴"
                )
            ) { doSecessionPositiveClick() }
            dialog.show(this.supportFragmentManager, dialog.tag)
        }
    }

    private fun doLogoutPositiveClick() {
        viewModel.removeAutoLoginState()
        startActivity(Intent(this, SignActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    private fun doSecessionPositiveClick() {
        viewModel.setDeleteAccount()
        startActivity(Intent(this, SignActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}