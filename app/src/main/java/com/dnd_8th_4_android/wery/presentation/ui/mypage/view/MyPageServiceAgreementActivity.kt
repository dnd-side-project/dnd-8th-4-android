package com.dnd_8th_4_android.wery.presentation.ui.mypage.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMypageServiceAgreementBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MyPageServiceAgreementActivity :
    BaseActivity<ActivityMypageServiceAgreementBinding>(R.layout.activity_mypage_service_agreement) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        binding.tvServiceAgreementContent.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/d7cdf86e96494920bfbbfd2aed297f48")
                )
            )
        }

        binding.tvPolicy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/0c3e82a2657d4d7282116f55d3b6386e")
                )
            )
        }

        binding.tvPositionTerms.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://emerald-calf-c8d.notion.site/b61e017d283949d99be9411a5d63828b")
                )
            )
        }
    }
}