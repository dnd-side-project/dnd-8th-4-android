package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ProfileChangeBottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class ProfileChangePopupBottomDialog(
    private val doAfterSelectGallery: () -> Unit,
    private val doAfterDeleteImg: () -> Unit,
) :
    BaseBottomDialogFragment<ProfileChangeBottomDialogPopupBinding>(R.layout.profile_change_bottom_dialog_popup) {

    override fun initAfterBinding() {
        binding.layoutAlbum.setOnClickListener {
            doAfterSelectGallery()
            dismiss()
        }

        binding.layoutRemove.setOnClickListener {
            doAfterDeleteImg()
            dismiss()
        }
    }
}