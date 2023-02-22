package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.Intent
import android.text.InputType
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignInBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.view.MainActivity
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignInViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val signInViewModel: SignInViewModel by viewModels()

    override fun initStartView() {
        binding.viewModel = signInViewModel
    }

    override fun initDataBinding() {
        signInViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoadingDialog(requireContext())
            else dismissLoadingDialog()
        }
    }

    override fun initAfterBinding() {
        setPWVisibility()
        setAutoLoginState()
        loginClickListener()

        signInViewModel.signInData.observe(viewLifecycleOwner) {
            if (it.data != null) {
                requireActivity().finish()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                showErrorDialog()
            }
        }
    }

    private fun setPWVisibility() {
        binding.ivBlind.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                binding.etvPw.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                it.isSelected = true
                binding.etvPw.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            binding.etvPw.setSelection(binding.etvPw.text.length)
        }

        binding.tvSignUp.setOnClickListener { goToSignUpUseAgreement() }
    }

    private fun goToSignUpUseAgreement() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpUseAgreementFragment)
    }

    private fun setAutoLoginState() {
        binding.tvAutoLogin.setOnClickListener {
            binding.cbAutoLogin.isChecked = !binding.cbAutoLogin.isChecked
        }
    }

    private fun loginClickListener() {
        // 자동 로그인 상태값 체크
        binding.btnLogin.setOnClickListener {
            setAutoLogin()
            signInViewModel.getSignInData()
        }
    }

    private fun setAutoLogin() {
        if (binding.cbAutoLogin.isChecked) signInViewModel.saveAutoLoginState(true)
        else signInViewModel.saveAutoLoginState(false)
    }

    private fun showErrorDialog() {
        val dialog = DialogFragmentUtil(
            DialogInfo(
                null,
                resources.getString(R.string.sign_in_error),
                null,
                resources.getString(R.string.sign_in_confirm)
            )
        ) {}

        dialog.show(childFragmentManager, dialog.tag)
    }
}