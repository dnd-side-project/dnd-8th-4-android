package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class SignUpNameFragment :
    BaseFragment<FragmentSignUpNameBinding>(R.layout.fragment_sign_up_name) {
    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener { goToSignUpEmail() }
    }

    private fun goToSignUpEmail() {
        findNavController().navigate(R.id.action_signUpNameFragment_to_signUpEmailFragment)
    }
}