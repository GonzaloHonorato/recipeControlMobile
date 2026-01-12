package com.example.recipecontrolmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.recipecontrolmobile.model.Recipe
import com.example.recipecontrolmobile.ui.screens.*
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

enum class Screen {
    Login, Register, Recover, Minuta, Detail
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeControlMobileTheme {
                var currentScreen by remember { mutableStateOf(Screen.Login) }
                var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

                when (currentScreen) {
                    Screen.Login -> LoginScreen(
                        onLoginSuccess = { currentScreen = Screen.Minuta },
                        onNavigateToRegister = { currentScreen = Screen.Register },
                        onNavigateToRecover = { currentScreen = Screen.Recover }
                    )
                    Screen.Register -> RegisterScreen(
                        onNavigateBack = { currentScreen = Screen.Login }
                    )
                    Screen.Recover -> RecoverPasswordScreen(
                        onNavigateBack = { currentScreen = Screen.Login }
                    )
                    Screen.Minuta -> MinutaScreen(
                        onLogout = { currentScreen = Screen.Login },
                        onRecipeClick = { recipe ->
                            selectedRecipe = recipe
                            currentScreen = Screen.Detail
                        }
                    )
                    Screen.Detail -> {
                        selectedRecipe?.let { recipe ->
                            RecipeDetailScreen(
                                recipe = recipe,
                                onBack = { currentScreen = Screen.Minuta }
                            )
                        }
                    }
                }
            }
        }
    }
}
