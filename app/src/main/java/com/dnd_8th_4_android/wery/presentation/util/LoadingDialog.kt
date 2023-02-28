package com.dnd_8th_4_android.wery.presentation.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.dnd_8th_4_android.wery.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setDimAmount(0.2f)
    }

    override fun show() {
        if(!this.isShowing) super.show()
    }
}