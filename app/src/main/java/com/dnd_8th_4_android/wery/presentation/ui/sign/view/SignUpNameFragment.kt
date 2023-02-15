package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpNameViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpNameFragment :
    BaseFragment<FragmentSignUpNameBinding>(R.layout.fragment_sign_up_name) {
    private val signUpNameViewModel: SignUpNameViewModel by viewModels()
    private val signViewModel: SignViewModel by activityViewModels()

    override fun initStartView() {
        binding.vm = signUpNameViewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            val edtPassword = binding.etName.text.toString()
            val pattern = Pattern.compile("^[가-힣]*\$")
            val isSuccess = pattern.matcher(edtPassword).find()
            if (isSuccess) {
                signViewModel.signUpName.value = signUpNameViewModel.signUpName.value
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

