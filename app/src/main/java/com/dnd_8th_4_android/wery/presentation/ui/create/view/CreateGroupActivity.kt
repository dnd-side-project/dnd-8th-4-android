package com.dnd_8th_4_android.wery.presentation.ui.create.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityCreateGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.create.viewmodel.CreateGroupViewModel

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
            binding.tvGroupNameLimit.text =
                getString(R.string.create_group_name_limit).format(it.length)
        }

        binding.etvGroupName.addTextChangedListener {
            if (it?.length!! > 12) binding.etvGroupName.setBackgroundResource(R.drawable.shape_white_radius_8_eb0555)
            else binding.etvGroupName.setBackgroundResource(R.drawable.shape_white_radius_8_black)
        }

        binding.etvGroupName.setOnFocusChangeListener { v, hasFocus ->
            if (!v.hasFocus()) binding.etvGroupName.setBackgroundResource(R.drawable.shape_white_radius_8_gray300)
        }
    }

    private fun setGroupIntroUi() {
        createGroupViewModel.groupIntroduceTxt.observe(this) {
            binding.tvGroupIntroduceLimit.text =
                getString(R.string.create_group_introduce_limit).format(it.length)
        }

        binding.etvGroupIntroduce.addTextChangedListener {
            if (it?.length!! > 25) binding.etvGroupIntroduce.setBackgroundResource(R.drawable.shape_white_radius_8_eb0555)
            else binding.etvGroupIntroduce.setBackgroundResource(R.drawable.shape_white_radius_8_black)
        }

        binding.etvGroupIntroduce.setOnFocusChangeListener { v, hasFocus ->
            if (!v.hasFocus()) binding.etvGroupIntroduce.setBackgroundResource(R.drawable.shape_white_radius_8_gray300)
        }

    }

    private fun initAfterBinding() {

    }
}