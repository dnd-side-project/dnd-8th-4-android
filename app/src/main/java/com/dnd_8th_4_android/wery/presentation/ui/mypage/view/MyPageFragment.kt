package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.FragmentMypageBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseFragment
import com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun initStartView() {
        viewModel.getMyInfo()

        binding.ivMyImage.clipToOutline = true
    }

    override fun initDataBinding() {
        viewModel.myInfoData.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.profileImageUrl)
                .into(binding.ivMyImage)

            binding.tvMyName.text = it.name
        }
    }

    override fun initAfterBinding() {
        binding.ivProfile.setOnClickListener {
            // TODO 프로필 수정
        }
    }
}