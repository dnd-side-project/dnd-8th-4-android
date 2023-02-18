package com.dnd_8th_4_android.wery.presentation.ui.create.view

import android.os.Bundle
import androidx.activity.viewModels
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.create.viewmodel.CreateGroupViewModel
import dagger.hilt.android.AndroidEntryPoint

class CreateGroupActivity :
    BaseActivity<ActivityCreateGroupBinding>(R.layout.activity_create_group) {

    private val createGroupViewModel: CreateGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initStartView() {
        binding.viewModel = createGroupViewModel
    }

    private fun initDataBinding() {
        setGroupNameUi()
        setGroupIntroUi()
    }

    private fun setGroupNameUi() {
        createGroupViewModel.groupNameTxt.observe(this) {
            binding.tvGroupNameLimit.text =  getString(R.string.create_group_name_limit).format(it.length)

            if (it.length > 12) {
                binding.tvGroupNameMsg.text = getString(R.string.create_group_name_error_cnt_msg)
            } else {
                binding.tvGroupNameMsg.text = getString(R.string.create_group_name_info)
            }
        }

    }

    private fun setGroupIntroUi() {

    }

    private fun initAfterBinding() {

    }
}