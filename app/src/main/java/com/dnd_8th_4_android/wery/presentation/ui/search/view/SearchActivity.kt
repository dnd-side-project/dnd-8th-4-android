package com.dnd_8th_4_android.wery.presentation.ui.search.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivitySearchBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initStartView()
    }

    private fun initStartView() {

    }
}