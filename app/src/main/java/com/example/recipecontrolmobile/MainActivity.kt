package com.example.recipecontrolmobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.example.recipecontrolmobile.ui.screens.*
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

enum class Screen {
    Login, Register, Recover
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            val systemInDark = isSystemInDarkTheme()

            LaunchedEffect(Unit) {
                isDarkMode = systemInDark
            }

            RecipeControlMobileTheme(darkTheme = isDarkMode) {
                var currentScreen by remember { mutableStateOf(Screen.Login) }

                when (currentScreen) {
                    Screen.Login -> LoginScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onLoginSuccess = { launchHome(isDarkMode) },
                        onNavigateToRegister = { currentScreen = Screen.Register },
                        onNavigateToRecover = { currentScreen = Screen.Recover }
                    )
                    Screen.Register -> RegisterScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onNavigateBack = { currentScreen = Screen.Login },
                        onRegisterSuccess = { launchHome(isDarkMode) }
                    )
                    Screen.Recover -> RecoverPasswordScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onNavigateBack = { currentScreen = Screen.Login }
                    )
                }
            }
        }
    }

    private fun launchHome(isDarkMode: Boolean) {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        intent.putExtra("isDarkMode", isDarkMode)
        startActivity(intent)
    }
}
