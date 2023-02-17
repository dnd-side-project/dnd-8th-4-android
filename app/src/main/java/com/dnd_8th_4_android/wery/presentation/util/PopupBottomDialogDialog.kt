package com.dnd_8th_4_android.wery.presentation.util

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.BottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class PopupBottomDialogDialog :
    BaseBottomDialogFragment<BottomDialogPopupBinding>(R.layout.bottom_dialog_popup) {

    override fun initAfterBinding() {
        binding.layerRemove.setOnClickListener {
            val postRemoveDialog = PostRemoveDialog()
            postRemoveDialog.show(childFragmentManager, null)
        }
    }
}