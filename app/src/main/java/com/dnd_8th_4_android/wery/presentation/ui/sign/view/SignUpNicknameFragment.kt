package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNicknameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.view.MainActivity
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
    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            Log.e("태그2", signViewModel.signUpName.value.toString())
            Log.e("태그2", signViewModel.signUpEmail.value.toString())
            Log.e("태그2", signViewModel.signUpPassword.value.toString())
//            TODO 이미 사용중인 별명인지 확인 기능 구현
//            if (true) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
//            } else {
//                binding.tvNicknameError.isVisible = true
//                binding.tvNicknameError.text = requireContext().resources.getString(R.string.sign_up_nickname_error)
//            }
        }
    }
}