package com.example.recipecontrolmobile.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

object RecipeRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("recipes")

    private val _recipesFlow = MutableStateFlow<List<Recipe>>(emptyList())
    val recipesFlow: StateFlow<List<Recipe>> = _recipesFlow

    fun getAllRecipes(): List<Recipe> = _recipesFlow.value

    fun getRandomRecipe(): Recipe =
        _recipesFlow.value.randomOrNull() ?: sampleRecipes.random()

    fun getRecipeById(id: Int): Recipe? = _recipesFlow.value.find { it.id == id }

    suspend fun loadFromFirestore() {
        try {
            val snapshot = collection.get().await()
            if (snapshot.isEmpty) {
                seedInitialRecipes()
                return
            }
            val recipes = snapshot.documents.mapNotNull { Recipe.fromDocument(it) }
            _recipesFlow.value = recipes
        } catch (e: Exception) {
            if (_recipesFlow.value.isEmpty()) {
                _recipesFlow.value = sampleRecipes
            }
        }
    }

    private suspend fun seedInitialRecipes() {
        try {
            sampleRecipes.forEach { recipe ->
                collection.add(recipe.toMap()).await()
            }
            loadFromFirestore()
        } catch (e: Exception) {
            _recipesFlow.value = sampleRecipes
        }
    }

    suspend fun addRecipe(recipe: Recipe) {
        try {
            collection.add(recipe.toMap()).await()
            loadFromFirestore()
        } catch (e: Exception) {
            _recipesFlow.value = _recipesFlow.value + recipe
        }
    }
}
