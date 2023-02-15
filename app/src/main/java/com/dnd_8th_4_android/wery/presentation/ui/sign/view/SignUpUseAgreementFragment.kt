package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpUseAgreementBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpUseAgreementViewModel

class SignUpUseAgreementFragment :
    BaseFragment<FragmentSignUpUseAgreementBinding>(R.layout.fragment_sign_up_use_agreement) {
    private val viewModel: SignUpUseAgreementViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.isSelectedBox.observe(viewLifecycleOwner) {
            binding.cbAllAgree.isChecked = it == 3
        }
    }

    override fun initAfterBinding() {
        binding.cbAllAgree.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                selectAll()
                viewModel.setSelectedBox(3)
                viewModel.setEnabled()
            } else {
                if (viewModel.isSelectedBox.value == 3) {
                    unSelectAll()
                    viewModel.setSelectedBox(0)
                    viewModel.setUnEnabled()
                }
            }
        }

        binding.btnNext.setOnClickListener { gotoSingUpName() }
    }

    private fun gotoSingUpName() {
        findNavController().navigate(R.id.action_signUpUseAgreementFragment_to_signUpNameFragment)
    }

    private fun selectAll() {
        binding.cbAge.isChecked = true
        binding.cbUseAgreement.isChecked = true
        binding.cbPrivateInformation.isChecked = true
    }

    private fun unSelectAll() {
        binding.cbAge.isChecked = false
        binding.cbUseAgreement.isChecked = false
        binding.cbPrivateInformation.isChecked = false
    }
}