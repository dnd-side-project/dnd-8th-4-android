package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNicknameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignUpNicknameViewModel
import com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel.SignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpNicknameFragment :
    BaseFragment<FragmentSignUpNicknameBinding>(R.layout.fragment_sign_up_nickname) {
    private val signUpNicknameViewModel: SignUpNicknameViewModel by viewModels()
    private val signViewModel: SignViewModel by activityViewModels()

    override fun initStartView() {
        binding.vm = signUpNicknameViewModel
    }

    override fun initDataBinding() {
        signUpNicknameViewModel.textCount.observe(viewLifecycleOwner) {
            if (it in 0..14) {
                binding.tvCount.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray600
                    )
                )
            } else {
                binding.tvNicknameError.isVisible = true
                binding.tvNicknameError.text =
                    requireContext().resources.getString(R.string.sign_up_nickname_count_error)
                binding.tvCount.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_eb0555
                    )
                )
            }
        }

        signUpNicknameViewModel.signUpSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_signUpNicknameFragment_to_signUpWelcomeFragment)
            }
        }
    }

    override fun initAfterBinding() {
        val hintList = requireContext().resources.getStringArray(R.array.nickname_hint).toList()
        binding.etNickname.hint = hintList.shuffled().take(1).joinToString()

        binding.btnNext.setOnClickListener {
            signUpNicknameViewModel.signUp(RequestSignUpData(
                name = signViewModel.signUpName.value.toString(),
                email = signViewModel.signUpEmail.value.toString(),
                password = signViewModel.signUpPassword.value.toString(),
                nickName = signUpNicknameViewModel.signUpNickname.value.toString()
            ))
        }
    }
}