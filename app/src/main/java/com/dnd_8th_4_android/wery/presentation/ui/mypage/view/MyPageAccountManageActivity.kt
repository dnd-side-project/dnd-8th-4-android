package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageAccountManageBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_EMAIL
import com.dnd_8th_4_android.wery.presentation.ui.mypage.view.MyPageFragment.Companion.USER_NAME

class MyPageAccountManageActivity :
    BaseActivity<ActivityMypageAccountManageBinding>(R.layout.activity_mypage_account_manage) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAfterBinding()
    }

    private fun initAfterBinding() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        binding.tvUserNameContent.text = intent.getStringExtra(USER_NAME)
        binding.tvUserEmailContent.text = intent.getStringExtra(USER_EMAIL)
    }
}