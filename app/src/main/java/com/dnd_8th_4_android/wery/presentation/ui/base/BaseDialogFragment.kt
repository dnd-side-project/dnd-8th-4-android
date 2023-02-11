package com.dnd_8th_4_android.wery.presentation.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import timber.log.Timber

abstract class BaseDialogFragment<T : ViewDataBinding>(@LayoutRes val layout: Int) :
    DialogFragment() {
    private var _binding: T? = null
    protected val binding get() = _binding ?: error("View를 참조하기 위해 binding이 초기화되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layout, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)

        initAfterBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun initAfterBinding()

    protected inner class LifeCycleEventLogger(private val className: String) : LifecycleObserver {
        fun registerLogger(lifecycle: Lifecycle) {
            lifecycle.addObserver(this)
        }

        fun log() {
            Timber.d("${className}LifeCycleEvent", "${lifecycle.currentState}")
        }
    }
}