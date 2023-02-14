package com.dnd_8th_4_android.wery.presentation.ui.sign.view

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentSignUpNicknameBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.home.view.MainActivity

class SignUpNicknameFragment :
    BaseFragment<FragmentSignUpNicknameBinding>(R.layout.fragment_sign_up_nickname) {
    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}