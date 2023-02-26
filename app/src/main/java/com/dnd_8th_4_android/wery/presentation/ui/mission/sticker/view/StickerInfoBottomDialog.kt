package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.view

import android.text.Spannable
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.DialogFragmentStickerInfoBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseBottomDialogFragment

class StickerInfoBottomDialog(
    private val title: String,
    private val level: Int,
    private val doAfterConfirm: () -> Unit
) :
    BaseBottomDialogFragment<DialogFragmentStickerInfoBinding>(R.layout.dialog_fragment_sticker_info) {

    override fun initAfterBinding() {
        setUi()
        setBtnEvent()
    }

    private fun setUi() {
        binding.apply {
            tvStickerName.text = title
            tvContent.text = resources.getString(R.string.sticker_dialog_content).format(level)
            (tvContent.text as Spannable).setSpan(
                ForegroundColorSpan(resources.getColor(R.color.color_f47aff, null)), 0, 2,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }

    private fun setBtnEvent() {
        binding.apply {
            btnShowSticker.setOnClickListener {
                dismiss()
                doAfterConfirm()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }
}