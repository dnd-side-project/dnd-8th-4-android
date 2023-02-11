package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.MainActivity
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySignInBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignInViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val signInViewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStartView()
        afterDataBinding()
    }

    private fun initStartView() {
        binding.viewModel = signInViewModel
    }

    private fun afterDataBinding() {
        setPWVisibility()
        setAutoLoginState()
        loginClickListener()
    }

    private fun setPWVisibility() {
        binding.ivBlind.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                binding.etvPw.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                it.isSelected = true
                binding.etvPw.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.etvPw.setSelection(binding.etvPw.text.length)
        }
    }

    private fun setAutoLoginState() {
        binding.tvAutoLogin.setOnClickListener {
            binding.cbAutoLogin.isChecked = !binding.cbAutoLogin.isChecked
        }
    }

    // TODO 추후 서버 통신 및 뷰모델 연결 예정
    private fun loginClickListener() {
        // 자동 로그인 상태값 체크
        binding.btnLogin.setOnClickListener {
            setAutoLogin()

            if (true) { // 홈화면 이동
                setAccessToken()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                showErrorDialog()
            }
        }

    }

    private fun setAccessToken() = signInViewModel.saveAccessToken("a")

    private fun setAutoLogin() {
        if (binding.cbAutoLogin.isChecked) signInViewModel.saveAutoLoginState(true)
        else signInViewModel.saveAutoLoginState(false)
    }
    private fun showErrorDialog() {
        val dialog = DialogFragmentUtil(
            DialogInfo(
                null,
                resources.getString(R.string.sign_in_error),
                null,
                resources.getString(R.string.sign_in_confirm)
            )
        ) {}

        dialog.show(supportFragmentManager, dialog.tag)
    }
}