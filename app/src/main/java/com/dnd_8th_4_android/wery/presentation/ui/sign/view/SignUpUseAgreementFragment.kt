package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.Intent
import android.net.Uri
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
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpUseAgreementFragment_to_signInFragment)
        }

        binding.ivServiceAgreement.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/d7cdf86e96494920bfbbfd2aed297f48")
                )
            )
        }

        binding.ivPolicy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/0c3e82a2657d4d7282116f55d3b6386e")
                )
            )
        }

        binding.ivPositionTerms.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/b61e017d283949d99be9411a5d63828b")
                )
            )
        }

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