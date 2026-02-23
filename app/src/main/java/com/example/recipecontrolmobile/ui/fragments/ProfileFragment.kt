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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.recipecontrolmobile.HomeActivity
import com.example.recipecontrolmobile.R
import com.example.recipecontrolmobile.model.UserRepository
import com.example.recipecontrolmobile.ui.theme.RecipeControlMobileTheme

class ProfileFragment : Fragment() {

    companion object {
        private const val ARG_DARK_MODE = "isDarkMode"

        fun newInstance(isDarkMode: Boolean): ProfileFragment {
            return ProfileFragment().apply {
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
                    ProfileContent(
                        onLogout = {
                            (requireActivity() as? HomeActivity)?.logout()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(onLogout: () -> Unit) {
    val currentUser = UserRepository.getCurrentUser()
    val userName = currentUser?.displayName?.ifBlank { null } ?: "Usuario"
    val userEmail = currentUser?.email ?: "Sin correo"

    var mealNotifications by remember { mutableStateOf(true) }
    var dailySuggestions by remember { mutableStateOf(false) }
    var waterGoal by remember { mutableFloatStateOf(8f) }
    var calorieRange by remember { mutableStateOf(1200f..2000f) }
    var rememberPrefs by remember { mutableStateOf(true) }
    var weeklyTips by remember { mutableStateOf(false) }
    var selectedDiet by remember { mutableStateOf("Vegano") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // ── Perfil de usuario ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    userName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Mis Metas ──
        Text(
            "Mis Metas",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Slider - Meta de agua
                Text(
                    "Meta de vasos de agua diarios: ${waterGoal.toInt()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = waterGoal,
                    onValueChange = { waterGoal = it },
                    valueRange = 1f..15f,
                    steps = 13
                )

                Spacer(modifier = Modifier.height(12.dp))

                // RangeSlider - Rango calórico
                Text(
                    "Rango calórico objetivo: ${calorieRange.start.toInt()} - ${calorieRange.endInclusive.toInt()} kcal",
                    style = MaterialTheme.typography.bodyMedium
                )
                RangeSlider(
                    value = calorieRange,
                    onValueChange = { calorieRange = it },
                    valueRange = 800f..3000f
                )

                Spacer(modifier = Modifier.height(12.dp))

                // LinearProgressIndicator - Progreso semanal
                Text(
                    "Recetas completadas esta semana: 5 de 7",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { 5f / 7f },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // CircularProgressIndicator - Meta diaria
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { 0.75f },
                        modifier = Modifier.size(40.dp),
                        strokeWidth = 4.dp
                    )
                    Column {
                        Text("Meta diaria: 75%", style = MaterialTheme.typography.titleSmall)
                        Text(
                            "1,500 de 2,000 kcal",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Preferencias ──
        Text(
            "Preferencias",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Switch - Notificaciones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notificaciones de comidas", modifier = Modifier.weight(1f))
                    Switch(checked = mealNotifications, onCheckedChange = { mealNotifications = it })
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Switch - Sugerencias
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sugerencias diarias", modifier = Modifier.weight(1f))
                    Switch(checked = dailySuggestions, onCheckedChange = { dailySuggestions = it })
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Checkbox - Recordar preferencias
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = rememberPrefs, onCheckedChange = { rememberPrefs = it })
                    Text("Recordar mis preferencias")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = weeklyTips, onCheckedChange = { weeklyTips = it })
                    Text("Recibir tips semanales")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Chips - Tipo de dieta
                Text(
                    "Tipo de dieta",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Vegano", "Keto", "Sin Gluten").forEach { label ->
                        FilterChip(
                            selected = selectedDiet == label,
                            onClick = { selectedDiet = label },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Cerrar Sesión ──
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
