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
                    "먹짱패밀리",
                    "https://mblogthumb-phinf.pstatic.net/MjAxNzA2MTRfMTM5/MDAxNDk3NDMzNzc3NzMz.bYz_7N23reM5QXP_MwApEZ6cMuP7HT0VF_FuE3j4bEYg.Lb1I_8WU9JFipAHpxlkSfHCGVnZ9ssflRaM1xgN2wGEg.JPEG.vazx1234/2017-06-14_18%3B45%3B09.JPG?type=w800"
                ),
            )
        binding.rvGroupList.adapter = selectGroupAdapter
        binding.tvGroupCnt.text = selectGroupAdapter.itemList.size.toString()
    }

    private fun getSelectedGroup(data: ResponseGroupList) {
        when (fromViewType) {
            "w" -> (viewModel as WritingViewModel).selectedGroup.value = data.groupName
            "m" -> (viewModel as CreateMissionViewModel).selectedGroup.value = data.groupName
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