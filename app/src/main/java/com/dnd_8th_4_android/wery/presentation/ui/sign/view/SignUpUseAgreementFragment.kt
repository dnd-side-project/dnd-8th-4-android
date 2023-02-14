package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpUseAgreementBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment

class SignUpUseAgreementFragment :
    BaseFragment<FragmentSignUpUseAgreementBinding>(R.layout.fragment_sign_up_use_agreement) {
    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener { gotoSingUpName() }
    }

    private fun gotoSingUpName() {
        findNavController().navigate(R.id.action_signUpUseAgreementFragment_to_signUpNameFragment)
    }
}