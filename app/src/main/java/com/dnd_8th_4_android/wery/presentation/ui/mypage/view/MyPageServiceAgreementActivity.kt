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
                    Uri.parse("https://wery.notion.site/wery/a6abcdf2e4784e05984ef419b51abbf1")
                )
            )
        }

        binding.tvPolicy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://wery.notion.site/wery/341fffc0d40f44aca488faecb375bcf2")
                )
            )
        }

        binding.tvPositionTerms.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://wery.notion.site/wery/f278b69d63aa4fed9782818315b7b7fc")
                )
            )
        }
    }
}