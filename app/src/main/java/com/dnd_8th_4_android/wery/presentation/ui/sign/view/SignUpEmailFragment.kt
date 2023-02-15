package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.util.Patterns
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpEmailBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpEmailViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil

class SignUpEmailFragment :
    BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {
    private val viewModel : SignUpEmailViewModel by viewModels()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                TODO 가입된 이메일인지 확인 기능 구현
//                if (true) {
                    goToSignUpPassword()
//                } else {
//                    showErrorDialog()
//                }
            } else {
                binding.tvEmailError.isVisible = true
            }
        }
    }

    private fun showErrorDialog() {
        val dialog = DialogFragmentUtil(
            DialogInfo(
                null,
                resources.getString(R.string.sign_up_email_dialog_error),
                null,
                resources.getString(R.string.sign_in_confirm)
            )
        ) {}

        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun goToSignUpPassword() {
        findNavController().navigate(R.id.action_signUpEmailFragment_to_signUpPasswordFragment)
    }
}