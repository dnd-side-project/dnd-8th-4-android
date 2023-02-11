package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.os.Bundle
import android.text.InputType
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySignInBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignInViewModel
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
}