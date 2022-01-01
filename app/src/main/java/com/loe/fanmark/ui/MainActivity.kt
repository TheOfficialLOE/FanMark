package com.loe.fanmark.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.loe.fanmark.R
import com.loe.fanmark.databinding.ActivityMainBinding
import com.loe.fanmark.util.Tools

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        smoothBarInit()
    }

    private fun smoothBarInit() {
        val popMenu = PopupMenu(this, null)
        popMenu.inflate(R.menu.nav_bar_menu)
        val menu = popMenu.menu
        binding.bottomBar.setupWithNavController(menu, navHostFragment.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        Tools.t("aaaaaaaaaaaaaaaaaaaaaa")
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

}