package com.example.recipecontrolmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipecontrolmobile.model.Recipe
import com.example.recipecontrolmobile.model.sampleRecipes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinutaScreen(
    onLogout: () -> Unit,
    onRecipeClick: (Recipe) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minuta Nutricional Semanal") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Salir", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Tus recetas para la semana",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Recipe Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(sampleRecipes) { recipe ->
                    RecipeCard(recipe, onClick = { onRecipeClick(recipe) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            NutritionalTable()
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = recipe.day, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Nutrición: ${recipe.nutritionalInfo}", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recipe.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = recipe.description, fontSize = 14.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ver pasos de preparación →",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun NutritionalTable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ) {
            Text("Recomendación General", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Text("Valor Sugerido", fontWeight = FontWeight.Bold)
        }
        
        HorizontalDivider(color = Color.LightGray)
        NutritionalRow("Agua diaria", "2 Litros")
        HorizontalDivider(color = Color.LightGray)
        NutritionalRow("Fibra total", "30g")
        HorizontalDivider(color = Color.LightGray)
        NutritionalRow("Proteína base", "0.8g/kg")
    }
}

@Composable
fun NutritionalRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Text(value)
    }
}
