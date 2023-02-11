package com.dnd_8th_4_android.wery.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.domain.model.SplashType
import com.dnd_8th_4_android.wery.presentation.ui.onboard.view.OnBoardingActivity
import com.dnd_8th_4_android.wery.presentation.ui.sign.view.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initStartView()
    }

    private fun initStartView() {
        val authLocalDataSource = AuthLocalDataSource(this)
        checkOnboardingState(authLocalDataSource)
    }

    private fun checkOnboardingState(authLocalDataSource: AuthLocalDataSource) {
        if (authLocalDataSource.hasOnboardDone) {
            checkAutoLoginState(authLocalDataSource)
        } else moveToNext(SplashType.ONBOARD)
    }

    private fun checkAutoLoginState(authLocalDataSource: AuthLocalDataSource) {
        if (authLocalDataSource.isAutoLogin) {
            moveToNext(SplashType.HOME)
        } else {
            moveToNext(SplashType.LOGIN)
        }
    }

    private fun moveToNext(type: SplashType) {
        when (type) {
            SplashType.LOGIN -> moveToNextHandler(Intent(this, SignInActivity::class.java))
            SplashType.ONBOARD -> moveToNextHandler(Intent(this, OnBoardingActivity::class.java))
            SplashType.HOME -> moveToNextHandler(Intent(this, OnBoardingActivity::class.java))
        }
    }

    private fun moveToNextHandler(intent: Intent) {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(intent)
                finish()
            }, 1000)
    }
}