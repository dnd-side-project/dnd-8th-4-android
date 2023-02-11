package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.view.View
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.BottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PopupBottomDialogDialog :
    BaseBottomDialogFragment<BottomDialogPopupBinding>(R.layout.bottom_dialog_popup) {

    override fun initAfterBinding() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}