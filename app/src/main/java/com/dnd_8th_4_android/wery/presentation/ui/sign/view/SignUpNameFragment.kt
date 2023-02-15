package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpNameViewModel
import java.util.regex.Pattern

class SignUpNameFragment :
    BaseFragment<FragmentSignUpNameBinding>(R.layout.fragment_sign_up_name) {
    private val viewModel : SignUpNameViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            val edtPassword = binding.etName.text.toString()
            val pattern = Pattern.compile("^[가-힣]*\$")
            val isSuccess = pattern.matcher(edtPassword).find()
            if (isSuccess) {
                goToSignUpEmail()
            } else {
                binding.tvNameError.isVisible = true
            }
        }
    }

    private fun goToSignUpEmail() {
        findNavController().navigate(R.id.action_signUpNameFragment_to_signUpEmailFragment)
    }
}

