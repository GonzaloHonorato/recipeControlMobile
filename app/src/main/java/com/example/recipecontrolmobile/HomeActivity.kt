package com.example.recipecontrolmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipecontrolmobile.ui.fragments.HomeFragment
import com.example.recipecontrolmobile.ui.fragments.MinutaFragment
import com.example.recipecontrolmobile.ui.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        isDarkMode = intent.getBooleanExtra("isDarkMode", false)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Cargar fragment inicial (Inicio)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment.newInstance(isDarkMode))
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment.newInstance(isDarkMode))
                    true
                }
                R.id.nav_minuta -> {
                    loadFragment(MinutaFragment.newInstance(isDarkMode))
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment.newInstance(isDarkMode))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun logout() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
