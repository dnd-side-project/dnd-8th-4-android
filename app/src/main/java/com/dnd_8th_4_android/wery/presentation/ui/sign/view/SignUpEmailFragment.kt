package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.util.Patterns
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpEmailBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.etEmail.requestFocus()
        binding.etEmail.setOnClickListener {
            val emailText = binding.etEmail.text.toString()
            binding.btnNext.isEnabled = emailText != ""
        }

        binding.etEmail.doBeforeTextChanged { text, _, _, _ ->
            val emailText = text.toString()
            binding.btnNext.isEnabled = emailText != ""
        }

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