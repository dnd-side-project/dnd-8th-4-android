package com.dnd_8th_4_android.wery.presentation.ui.home.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.databinding.ActivityMainBinding
import com.dnd_8th_4_android.wery.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
    }

    private fun initStartView() {
        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostHome) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)*/

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostHome) as NavHostFragment? ?: return
        val navController = host.navController

        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            return@setOnItemSelectedListener true
        }
    }
}