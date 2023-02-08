package com.dnd_8th_4_android.wery

import android.os.Bundle
import com.dnd_8th_4_android.wery.databinding.ActivityMainBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initStartView() {}

    override fun initDataBinding() {}

    override fun initAfterBinding() {}
}