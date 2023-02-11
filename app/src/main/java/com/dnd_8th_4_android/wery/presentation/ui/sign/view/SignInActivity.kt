package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.res.Resources.Theme
import android.os.Bundle
import android.widget.Toast
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
        initDataBinding()
        afterDataBinding()
    }

    private fun initStartView() {
        binding.viewModel = signInViewModel
    }

    private fun initDataBinding() {
    }

    private fun afterDataBinding() {
    }
}