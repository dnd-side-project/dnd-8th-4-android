package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpWelcomeBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class SignUpWelcomeFragment :
    BaseFragment<FragmentSignUpWelcomeBinding>(R.layout.fragment_sign_up_welcome) {

    override fun initStartView() {}

    override fun initDataBinding() {}

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener { goToSignIn() }
    }

    private fun goToSignIn() {
        findNavController().navigate(R.id.action_signUpWelcomeFragment_to_signInFragment)
    }
}