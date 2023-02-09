package com.dnd_8th_4_android.wery

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.dnd_8th_4_android.wery.databinding.ActivityMainBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun initStartView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostHome) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}