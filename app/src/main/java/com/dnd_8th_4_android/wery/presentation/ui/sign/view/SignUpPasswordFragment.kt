package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpPasswordBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class SignUpPasswordFragment :
    BaseFragment<FragmentSignUpPasswordBinding>(R.layout.fragment_sign_up_password) {
    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener { goToSignUpNickname() }
    }

    private fun goToSignUpNickname() {
        findNavController().navigate(R.id.action_signUpPasswordFragment_to_signUpNicknameFragment)
    }
}