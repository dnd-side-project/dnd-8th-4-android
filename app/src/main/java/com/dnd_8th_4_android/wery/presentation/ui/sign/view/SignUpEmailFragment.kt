package com.dnd_8th_4_android.wery.presentation.ui.sign.view

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
        binding.btnNext.setOnClickListener { goToSignUpPassword() }
    }

    private fun goToSignUpPassword() {
        findNavController().navigate(R.id.action_signUpEmailFragment_to_signUpPasswordFragment)
    }
}