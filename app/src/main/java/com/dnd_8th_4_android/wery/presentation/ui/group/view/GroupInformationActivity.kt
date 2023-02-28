package com.dnd_8th_4_android.wery.presentation.ui.group.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityGroupInformationBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupInformationRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.adapter.GroupListRecyclerViewAdapter
import com.dnd_8th_4_android.wery.presentation.ui.group.create.view.CreateGroupActivity
import com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel.GroupInformationViewModel
import com.dnd_8th_4_android.wery.presentation.util.DialogFragmentUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupInformationActivity :
    BaseActivity<ActivityGroupInformationBinding>(R.layout.activity_group_information) {
    private val viewModel: GroupInformationViewModel by viewModels()
    private lateinit var groupInformationRecyclerViewAdapter: GroupInformationRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        viewModel.isSelectGroupId.value =
            intent.getStringExtra(GroupListRecyclerViewAdapter.GROUP_Id)
        viewModel.getGroupInformation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }

    private fun initDataBinding() {
        viewModel.isLoading.observe(this) {
            if (it) showLoadingDialog()
            else dismissLoadingDialog()
        }

        viewModel.groupList.observe(this) {
            groupInformationRecyclerViewAdapter.submitList(it.groupMemberInfoList)

            binding.ivGroupImage.clipToOutline = true
            Glide.with(binding.ivGroupImage.context).load(it.groupImageUrl)
                .into(binding.ivGroupImage)

            binding.tvGroupName.text = it.groupName
            binding.tvGroupIntroduceContent.text = it.groupNote
            binding.tvGroupCreateAt.text = it.groupCreatedAt
            binding.tvGroupListCount.text = it.groupMemberInfoList.size.toString()
        }
    }

    private fun initStartView() {
        groupInformationRecyclerViewAdapter = GroupInformationRecyclerViewAdapter()
        binding.rvGroupList.apply {
            adapter = groupInformationRecyclerViewAdapter
            itemAnimator = null
            isNestedScrollingEnabled = false
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivSearchInvite.setOnClickListener {
            Intent(this, UserSearchActivity::class.java).apply {
                putExtra(GroupListRecyclerViewAdapter.GROUP_Id, viewModel.isSelectGroupId.value)
                startActivity(this)
            }
        }

        binding.layerOut.setOnClickListener {
            val dialog = DialogFragmentUtil(
                DialogInfo(
                    resources.getString(R.string.group_information_out_dialog_title),
                    resources.getString(R.string.group_information_out_dialog_content),
                    "취소",
                    resources.getString(R.string.group_information_out_dialog_confirm)
                )
            ) {
                viewModel.deleteGroup()
                finish()
            }
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    private fun initAfterBinding() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivConfiguration.setOnClickListener {
            val intent = Intent(this, CreateGroupActivity::class.java)
            intent.putExtra("groupId", viewModel.isSelectGroupId.value?.toInt())
            startActivity(intent)
        }
    }
}