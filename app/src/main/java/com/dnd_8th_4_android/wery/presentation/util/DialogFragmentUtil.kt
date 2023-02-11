package com.dnd_8th_4_android.wery.presentation.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.DialogFragmentBinding
import com.dnd_8th_4_android.wery.domain.model.DialogInfo

class DialogFragmentUtil(
    private val dialogInfo: DialogInfo,
    private val doPositiveClick: () -> Unit
) : DialogFragment() {

    private var _binding: DialogFragmentBinding? = null
    private val binding get() = _binding ?: error("View를 참조하기 위해 binding이 초기화되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment,
            container,
            false
        )

        initStartView()
        initDataBinding()
        initAfterBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initStartView() {
        setDialogLayout()
    }

    private fun initDataBinding() {
        setComponentVisibility()
        setDialogTxt()
    }

    private fun initAfterBinding() {
        setPositiveClick()
        setNegativeClick()
    }

    private fun setDialogLayout() {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.requestFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
        }
    }

    private fun setComponentVisibility() {
        if (dialogInfo.btnNegative == null) binding.btnNegative.visibility = View.GONE
        if (dialogInfo.title == null) binding.tvTitle.visibility = View.GONE
    }

    private fun setDialogTxt() {
        binding.apply {
            if (dialogInfo.title != null) tvTitle.text = dialogInfo.title
            if (dialogInfo.btnNegative != null) btnNegative.text = dialogInfo.btnNegative
            tvText.text = dialogInfo.text
            btnPositive.text = dialogInfo.btnPositive
        }
    }

    private fun setPositiveClick() {
        binding.btnPositive.setOnClickListener {
            dismiss()
            doPositiveClick()
        }
    }

    private fun setNegativeClick() {
        binding.btnNegative.setOnClickListener {
            dismiss()
        }
    }
}