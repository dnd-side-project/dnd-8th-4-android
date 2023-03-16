package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.content.Intent
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

    companion object {
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_IMAGE = "user_image"
        const val USER_NICKNAME = "user_nickname"
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMyInfo()
    }

    override fun initStartView() {
        binding.ivMyImage.clipToOutline = true
    }

    override fun initDataBinding() {
        viewModel.myInfoData.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.profileImageUrl)
                .into(binding.ivMyImage)

            binding.tvMyName.text = it.nickName

            binding.ivConfiguration.setOnClickListener { _ ->
                Intent(requireContext(), MyPageConfigurationActivity::class.java).apply {
                    putExtra(USER_NAME, it.name)
                    putExtra(USER_EMAIL, it.email)
                    startActivity(this)
                }
            }

            binding.ivProfile.setOnClickListener { _ ->
                Intent(requireContext(), ProfileChangeActivity::class.java).apply {
                    putExtra(USER_IMAGE, it.profileImageUrl)
                    putExtra(USER_NICKNAME, it.nickName)
                    startActivity(this)
                }
            }

            binding.ivMission.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        MyPageMissionConstructionActivity::class.java
                    )
                )
            }

            binding.ivBookmark.setOnClickListener {
                startActivity(Intent(requireContext(), MyPageBookmarkActivity::class.java))
            }

            binding.ivPost.setOnClickListener {
                startActivity(Intent(requireContext(), MyPagePostConstructionActivity::class.java))
            }

            binding.ivComment.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        MyPageCommentConstructionActivity::class.java
                    )
                )
            }

            binding.ivNotice.setOnClickListener {
                startActivity(Intent(requireContext(), MyPageNoticeActivity::class.java))
            }
        }
    }

    override fun initAfterBinding() {}
}