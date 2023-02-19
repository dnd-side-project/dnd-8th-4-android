package com.dnd_8th_4_android.wery.presentation.ui.group.create.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.DialogFragmentSelectGroupImgBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class SelectGroupImgBottomDialog(
    private val doAfterSelectGallery: () -> Unit,
    private val doAfterDeleteImg: () -> Unit
) :
    BaseBottomDialogFragment<DialogFragmentSelectGroupImgBinding>(R.layout.dialog_fragment_select_group_img) {

    override fun initAfterBinding() {
        binding.layoutSelectGallery.setOnClickListener {
            doAfterSelectGallery()
            dismiss()
        }

        binding.layoutRemoveImg.setOnClickListener {
            doAfterDeleteImg()
            dismiss()
        }
    }
}