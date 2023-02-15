package com.dnd_8th_4_android.wery.presentation.ui.write.upload.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.databinding.DialogFragmentSelectGroupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.adapter.SelectGroupAdapter
import com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel.WritingViewModel

class SelectGroupBottomDialog(private val viewModel: WritingViewModel) :
    BaseBottomDialogFragment<DialogFragmentSelectGroupBinding>(R.layout.dialog_fragment_select_group) {

    private lateinit var selectGroupAdapter: SelectGroupAdapter

    override fun initAfterBinding() {
        selectGroupAdapter = SelectGroupAdapter { data -> getSelectedGroup(data) }
        selectGroupAdapter.itemList =
            mutableListOf<ResponseGroupList>(
                ResponseGroupList(
                    0L,
                    "Group1",
                    "https://static.wikia.nocookie.net/pokemon/images/0/09/%EB%A9%94%ED%83%80%EB%AA%BD_%EA%B3%B5%EC%8B%9D_%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8.png/revision/latest?cb=20170405084916&path-prefix=ko"
                ),
                ResponseGroupList(
                    0L,
                    "Group2",
                    "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/3diH/image/u7DLCD7ckwu61VBqlNMQpdxTBZM.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group3",
                    "https://blog.kakaocdn.net/dn/bWeA1K/btrx6nQb6Gu/GdaYmAPaO2LG839DsIigQk/img.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group4",
                    "https://m.ezendolls.com/web/product/big/202107/9cbb94e6c811f5ade35cf3c7976d774b.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group5",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlT4C48M9Ah2f6NKRG9YlIjWX3WXOVwdtvUA&usqp=CAU"
                ),
                ResponseGroupList(
                    0L,
                    "Group6",
                    "https://i1.sndcdn.com/avatars-sn8HgEgiucAccV0D-yogYJA-t240x240.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group7",
                    "https://i1.sndcdn.com/avatars-sn8HgEgiucAccV0D-yogYJA-t240x240.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group8",
                    "https://i1.sndcdn.com/avatars-sn8HgEgiucAccV0D-yogYJA-t240x240.jpg"
                ),
                ResponseGroupList(
                    0L,
                    "Group9",
                    "https://i1.sndcdn.com/avatars-sn8HgEgiucAccV0D-yogYJA-t240x240.jpg"
                )
            )
        binding.rvGroupList.adapter = selectGroupAdapter
    }

    private fun getSelectedGroup(data: ResponseGroupList) {
        viewModel.selectedGroup.value = data.groupName
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.selectedGroupState.value = false
    }
}