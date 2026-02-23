package com.example.recipecontrolmobile.ui.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipecontrolmobile.model.UserRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var registerError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("Femenino") }
    var acceptTerms by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val levels = listOf("Principiante", "Intermedio", "Avanzado")
    var expanded by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(levels[0]) }

    val gradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.background)
    )

    fun validateEmail(text: String): Boolean {
        return if (text.isBlank()) {
            emailError = "El correo no puede estar vacío"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            emailError = "Formato de correo inválido"
            false
        } else {
            emailError = null
            true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    TextButton(onClick = onThemeToggle) {
                        Text(if (isDarkMode) "MODO CLARO" else "MODO OSCURO")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Únete a NutriControl",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Comienza tu viaje hacia una vida sana",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (registerError != null) {
                Text(
                    text = registerError!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre Completo") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it.trim()
                            registerError = null
                            if (emailError != null) validateEmail(email)
                        },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = emailError != null,
                        supportingText = { emailError?.let { Text(it) } },
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Género", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedGender == "Masculino", onClick = { selectedGender = "Masculino" })
                        Text("Masculino")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(selected = selectedGender == "Femenino", onClick = { selectedGender = "Femenino" })
                        Text("Femenino")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = selectedLevel,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Nivel de cocina") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            levels.forEach { level ->
                                DropdownMenuItem(
                                    text = { Text(level) },
                                    onClick = {
                                        selectedLevel = level
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = acceptTerms, onCheckedChange = { acceptTerms = it })
                        Text("Acepto términos y condiciones", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (validateEmail(email) && name.isNotBlank() && password.isNotBlank()) {
                                isLoading = true
                                registerError = null
                                scope.launch {
                                    val result = UserRepository.register(email, password, name)
                                    isLoading = false
                                    result.fold(
                                        onSuccess = { onRegisterSuccess() },
                                        onFailure = { e ->
                                            registerError = when {
                                                e.message?.contains("already in use", ignoreCase = true) == true ->
                                                    "Ya existe una cuenta con ese correo"
                                                e.message?.contains("weak password", ignoreCase = true) == true ->
                                                    "La contraseña debe tener al menos 6 caracteres"
                                                e.message?.contains("network", ignoreCase = true) == true ->
                                                    "Error de conexión. Verifica tu internet"
                                                else -> "Error al crear cuenta: ${e.message}"
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = acceptTerms && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("REGISTRARSE", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateBack) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}
