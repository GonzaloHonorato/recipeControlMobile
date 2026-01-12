package com.example.recipecontrolmobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Femenino") }
    var acceptTerms by remember { mutableStateOf(false) }
    
    val levels = listOf("Principiante", "Intermedio", "Avanzado")
    var expanded by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(levels[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro de Usuario", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
        
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio Buttons for Gender
        Text("Género:", modifier = Modifier.align(Alignment.Start))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedGender == "Masculino", onClick = { selectedGender = "Masculino" })
            Text("Masculino")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = selectedGender == "Femenino", onClick = { selectedGender = "Femenino" })
            Text("Femenino")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Combo Box (Exposed Dropdown Menu)
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
                modifier = Modifier.menuAnchor().fillMaxWidth()
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

        // Checkbox (Check list component)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = acceptTerms, onCheckedChange = { acceptTerms = it })
            Text("Acepto los términos y condiciones")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Handle Register */ },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("REGISTRARSE")
        }

        TextButton(onClick = onNavigateBack) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
