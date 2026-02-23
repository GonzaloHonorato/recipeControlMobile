package com.example.recipecontrolmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.recipecontrolmobile.R
import com.example.recipecontrolmobile.model.Recipe
import com.example.recipecontrolmobile.ui.screens.AddRecipeScreen
import com.example.recipecontrolmobile.ui.screens.MinutaScreen
import com.example.recipecontrolmobile.ui.screens.RecipeDetailScreen
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

private enum class MinutaSubScreen {
    List, Detail, AddRecipe
}

class MinutaFragment : Fragment() {

    companion object {
        private const val ARG_DARK_MODE = "isDarkMode"

        fun newInstance(isDarkMode: Boolean): MinutaFragment {
            return MinutaFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_DARK_MODE, isDarkMode)
                }
            }
        }
    }

    // Estado de navegacion interna compartido con Compose
    private var currentSubScreen = mutableStateOf(MinutaSubScreen.List)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Manejar back press dentro del fragment
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (currentSubScreen.value != MinutaSubScreen.List) {
                        currentSubScreen.value = MinutaSubScreen.List
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isDarkMode = arguments?.getBoolean(ARG_DARK_MODE) ?: false

        view.findViewById<ComposeView>(R.id.compose_view).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecipeControlMobileTheme(darkTheme = isDarkMode) {
                    MinutaFragmentContent(
                        isDarkMode = isDarkMode,
                        currentSubScreen = currentSubScreen
                    )
                }
            }
        }
    }
}

@Composable
private fun MinutaFragmentContent(
    isDarkMode: Boolean,
    currentSubScreen: MutableState<MinutaSubScreen>
) {
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    when (currentSubScreen.value) {
        MinutaSubScreen.List -> MinutaScreen(
            isDarkMode = isDarkMode,
            onThemeToggle = { },
            onLogout = { },
            onRecipeClick = { recipe ->
                selectedRecipe = recipe
                currentSubScreen.value = MinutaSubScreen.Detail
            },
            onAddRecipeClick = {
                currentSubScreen.value = MinutaSubScreen.AddRecipe
            }
        )
        MinutaSubScreen.Detail -> {
            selectedRecipe?.let { recipe ->
                RecipeDetailScreen(
                    isDarkMode = isDarkMode,
                    onThemeToggle = { },
                    recipe = recipe,
                    onBack = { currentSubScreen.value = MinutaSubScreen.List }
                )
            }
        }
        MinutaSubScreen.AddRecipe -> AddRecipeScreen(
            isDarkMode = isDarkMode,
            onThemeToggle = { },
            onBack = { currentSubScreen.value = MinutaSubScreen.List }
        )
    }
}
