package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.text.InputType
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpPasswordBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpPasswordViewModel
import java.util.regex.Pattern

class SignUpPasswordFragment :
    BaseFragment<FragmentSignUpPasswordBinding>(R.layout.fragment_sign_up_password) {
    private val viewModel: SignUpPasswordViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.textCount.observe(viewLifecycleOwner) {
            if (it in 0..12) {
                binding.tvCount.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            } else {
                binding.tvCount.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_eb0555
                    )
                )
            }
        }
    }

    override fun initAfterBinding() {
        binding.ivBlind.setOnClickListener {
            it.isSelected = !it.isSelected
            viewModel.togglePasswordVisible()
            changeInputType()
        }

        binding.btnNext.setOnClickListener {
            if (viewModel.textCount.value in 8..12) {
                val edtPassword = binding.etPassword.text.toString()
                val pattern = Pattern.compile("^[a-zA-Z\\d!@#$%^&+=]*$")
                val isSuccess = pattern.matcher(edtPassword).find()
                if (isSuccess) {
                    goToSignUpNickname()
                } else {
                    binding.tvPasswordError.isVisible = true
                }
            }
        }
    }

    private fun changeInputType() {
        if (viewModel.isPasswordVisible.value == true) {
            binding.etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            binding.etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        binding.etPassword.setSelection(binding.etPassword.length())
    }

    private fun goToSignUpNickname() {
        findNavController().navigate(R.id.action_signUpPasswordFragment_to_signUpNicknameFragment)
    }
}