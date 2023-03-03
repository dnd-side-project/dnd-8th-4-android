package com.dnd_8th_4_android.wery.presentation.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.dnd_8th_4_android.wery.presentation.util.LoadingDialog
import timber.log.Timber

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    AppCompatActivity() {
    protected lateinit var binding: T
    private val mLoadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this

        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
        overridePendingTransition(0, 0)
    }

    fun showLoadingDialog() {
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        mLoadingDialog.dismiss()
    }

    protected inner class LifeCycleEventLogger(private val className: String) : LifecycleObserver {
        fun registerLogger(lifecycle: Lifecycle) {
            lifecycle.addObserver(this)
        }

        fun log() {
            Timber.d("${className}LifeCycleEvent", "${lifecycle.currentState}")
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}