package com.dnd_8th_4_android.wery.presentation.ui.group.view

import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.InviteBottomDialogPopupBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class InvitePopupBottomDialog :
    BaseBottomDialogFragment<InviteBottomDialogPopupBinding>(R.layout.invite_bottom_dialog_popup) {
    private lateinit var onInviteListener: OnInviteListener

    override fun initAfterBinding() {

        binding.layoutInvite.setOnClickListener {
            onInviteListener.onClicked()
            dialog!!.dismiss()
        }

        binding.layoutClip.setOnClickListener {
            showToast(resources.getString(R.string.bottom_sheet_clip_toast))
            dialog!!.dismiss()
        }
    }

    fun setOnInviteListener(listener: () -> Unit) {
        onInviteListener = object : OnInviteListener {
            override fun onClicked() {
                listener()
            }
        }
    }

    interface OnInviteListener {
        fun onClicked()
    }
}