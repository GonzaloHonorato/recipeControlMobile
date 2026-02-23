package com.example.recipecontrolmobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.recipecontrolmobile.R
import com.example.recipecontrolmobile.model.RecipeRepository
import com.example.recipecontrolmobile.model.UserRepository
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

class HomeFragment : Fragment() {

    companion object {
        private const val ARG_DARK_MODE = "isDarkMode"

        fun newInstance(isDarkMode: Boolean): HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_DARK_MODE, isDarkMode)
                }
            }
        }
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
                    HomeContent()
                }
            }
        }
    }
}

@Composable
fun HomeContent() {
    val recipes by RecipeRepository.recipesFlow.collectAsState()
    val suggestion = remember(recipes) { recipes.randomOrNull() }
    val currentUser = UserRepository.getCurrentUser()
    val userName = currentUser?.displayName?.ifBlank { null } ?: "Usuario"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // ── Bienvenida ──
        Text(
            text = "Hola, $userName",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        // bodyLarge
        Text(
            text = "Aquí está tu resumen nutricional de hoy",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Calorías del día ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // labelMedium
                Text(
                    text = "CALORÍAS CONSUMIDAS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                // displayLarge
                Text(
                    text = "1,850",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                // bodySmall
                Text(
                    text = "de 2,000 kcal diarias",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Button (Filled)
                Button(onClick = {}) {
                    Text("Ver Plan Nutricional")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Progreso de hoy ──
        // headlineMedium
        Text(
            text = "Tu Progreso Hoy",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 3 tarjetas de stats con displaySmall + titleSmall
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // displaySmall
                    Text(
                        text = "8",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    // titleSmall
                    Text(
                        text = "Vasos de Agua",
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "3",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = "Comidas",
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${recipes.size}",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Recetas",
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Meta semanal ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    // displayMedium
                    Text(
                        text = "75%",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        // titleMedium
                        Text(
                            text = "Meta Semanal",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        // bodyMedium
                        Text(
                            text = "Has completado 15 de 20 comidas planificadas",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // ElevatedButton
                    ElevatedButton(onClick = {}) {
                        Text("Ver Progreso")
                    }
                    // FilledTonalButton
                    FilledTonalButton(onClick = {}) {
                        Text("Compartir")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Receta sugerida ──
        // headlineSmall
        Text(
            text = "Receta Sugerida",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (suggestion != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = suggestion.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = suggestion.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = suggestion.nutritionalInfo,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(onClick = {}) {
                            Text("Ver Receta")
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Guardar en favoritos",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Plan semanal preview ──
        // labelLarge (usado implícito en botones, pero también explícito)
        Text(
            text = "PLAN DE LA SEMANA",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        recipes.take(3).forEach { recipe ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = recipe.day, style = MaterialTheme.typography.titleSmall)
                Text(text = recipe.name, style = MaterialTheme.typography.bodySmall)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // TextButton
        TextButton(onClick = {}) {
            Text("Ver minuta completa →")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
