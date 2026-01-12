package com.example.recipecontrolmobile.model

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val nutritionalInfo: String,
    val day: String,
    val instructions: List<String> = listOf(
        "Lavar bien todos los ingredientes.",
        "Preparar los utensilios necesarios.",
        "Cocinar a fuego medio por 20 minutos.",
        "Servir caliente y disfrutar."
    )
)

val sampleRecipes = listOf(
    Recipe(
        1, "Ensalada César", "Una ensalada clásica con pollo a la parrilla.",
        listOf("Lechuga", "Pollo", "Crutones", "Queso Parmesano"),
        "250 kcal, 20g Proteína", "Lunes",
        listOf("Lavar la lechuga y cortarla.", "Cocinar el pollo a la plancha.", "Mezclar ingredientes en un bowl.", "Agregar aderezo y queso.")
    ),
    Recipe(
        2, "Sopa de Verduras", "Sopa nutritiva con vegetales de estación.",
        listOf("Zanahoria", "Apio", "Zapallo", "Papas"),
        "150 kcal, 5g Fibra", "Martes",
        listOf("Cortar verduras en cubos.", "Hervir agua con sal.", "Cocinar verduras hasta que estén blandas.", "Licuar si prefiere crema.")
    ),
    Recipe(
        3, "Pescado al Horno", "Salmón con finas hierbas y limón.",
        listOf("Salmón", "Limón", "Romero", "Sal de mar"),
        "300 kcal, 30g Proteína", "Miércoles",
        listOf("Precalentar el horno a 180°C.", "Aliñar el salmón con limón y romero.", "Hornear por 15-20 minutos.", "Servir con acompañamiento.")
    ),
    Recipe(
        4, "Pasta Integral", "Pasta con salsa pomodoro natural.",
        listOf("Pasta integral", "Tomates", "Albahaca", "Ajo"),
        "350 kcal, 60g Carbohidratos", "Jueves",
        listOf("Cocer la pasta al dente.", "Preparar salsa con tomates y ajo.", "Mezclar pasta con la salsa.", "Decorar con albahaca fresca.")
    ),
    Recipe(
        5, "Tacos de Legumbres", "Tortillas de maíz con porotos negros y palta.",
        listOf("Tortillas", "Porotos negros", "Palta", "Cebolla"),
        "280 kcal, 15g Proteína", "Viernes",
        listOf("Calentar las tortillas.", "Preparar el relleno de porotos.", "Picar la palta y cebolla.", "Armar los tacos a gusto.")
    )
)
