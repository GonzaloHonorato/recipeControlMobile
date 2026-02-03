package com.example.recipecontrolmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.example.recipecontrolmobile.model.Recipe
import com.example.recipecontrolmobile.ui.screens.*
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

enum class Screen {
    Login, Register, Recover, Minuta, Detail, AddRecipe
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Estado global para el tema
            var isDarkMode by remember { mutableStateOf(false) }
            val systemInDark = isSystemInDarkTheme()
            
            LaunchedEffect(Unit) {
                isDarkMode = systemInDark
            }

            RecipeControlMobileTheme(darkTheme = isDarkMode) {
                var currentScreen by remember { mutableStateOf(Screen.Login) }
                var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

                when (currentScreen) {
                    Screen.Login -> LoginScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onLoginSuccess = { currentScreen = Screen.Minuta },
                        onNavigateToRegister = { currentScreen = Screen.Register },
                        onNavigateToRecover = { currentScreen = Screen.Recover }
                    )
                    Screen.Register -> RegisterScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onNavigateBack = { currentScreen = Screen.Login },
                        onRegisterSuccess = { currentScreen = Screen.Minuta }
                    )
                    Screen.Recover -> RecoverPasswordScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onNavigateBack = { currentScreen = Screen.Login }
                    )
                    Screen.Minuta -> MinutaScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onLogout = { currentScreen = Screen.Login },
                        onRecipeClick = { recipe ->
                            selectedRecipe = recipe
                            currentScreen = Screen.Detail
                        },
                        onAddRecipeClick = { currentScreen = Screen.AddRecipe }
                    )
                    Screen.Detail -> {
                        selectedRecipe?.let { recipe ->
                            RecipeDetailScreen(
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = !isDarkMode },
                                recipe = recipe,
                                onBack = { currentScreen = Screen.Minuta }
                            )
                        }
                    }
                    Screen.AddRecipe -> AddRecipeScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        onBack = { currentScreen = Screen.Minuta }
                    )
                }
            }
        }
    }
}
