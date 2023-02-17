package com.dnd_8th_4_android.wery.presentation.util

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.DialogFragmentRemoveBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseDialogFragment

class PostRemoveDialog :
    BaseDialogFragment<DialogFragmentRemoveBinding>(R.layout.dialog_fragment_remove) {

    override fun initAfterBinding() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }
}