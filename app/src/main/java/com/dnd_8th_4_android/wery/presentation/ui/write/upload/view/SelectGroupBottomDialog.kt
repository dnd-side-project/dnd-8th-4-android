package com.dnd_8th_4_android.wery.presentation.ui.write.upload.view

import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.databinding.DialogFragmentSelectGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment
import com.dnd_8th_4_android.wery.presentation.ui.mission.create.CreateMissionViewModel
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter.SelectGroupAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel.WritingViewModel

class SelectGroupBottomDialog(
    private val viewModel: ViewModel,
    private val fromViewType: String
) :
    BaseBottomDialogFragment<DialogFragmentSelectGroupBinding>(R.layout.dialog_fragment_select_group) {

    private lateinit var selectGroupAdapter: SelectGroupAdapter

    override fun initAfterBinding() {
        selectGroupAdapter = SelectGroupAdapter { data -> getSelectedGroup(data) }
        (viewModel as WritingViewModel).groupListData.observe(this) {
            selectGroupAdapter.itemList = it
            binding.tvGroupCnt.text = it.size.toString()
        }
        binding.rvGroupList.adapter = selectGroupAdapter
    }

    private fun getSelectedGroup(data: ResponseGroupList.ResultGroupList) {
        when (fromViewType) {
            "w" -> {
                (viewModel as WritingViewModel).selectedGroup.value = data.groupName
                viewModel.setGroupId(data.id)
            }
            "m" -> {
                (viewModel as CreateMissionViewModel).selectedGroup.value = data.groupName
                // viewModel.setGroupId(data.id)
            }
        }
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        when (fromViewType) {
            "w" -> (viewModel as WritingViewModel).selectedGroupState.value = false
            "m" -> (viewModel as CreateMissionViewModel).selectedGroupState.value = false
        }
    }
}