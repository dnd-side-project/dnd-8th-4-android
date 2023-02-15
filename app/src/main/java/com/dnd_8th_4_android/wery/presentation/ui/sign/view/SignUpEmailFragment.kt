package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.util.Patterns
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpEmailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpEmailViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpNameViewModel
import java.util.regex.Pattern

class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {
    private val viewModel : SignUpEmailViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                goToSignUpPassword()
            } else {
                binding.tvEmailError.isVisible = true
            }
        }
    }

    private fun goToSignUpPassword() {
        findNavController().navigate(R.id.action_signUpEmailFragment_to_signUpPasswordFragment)
    }
}