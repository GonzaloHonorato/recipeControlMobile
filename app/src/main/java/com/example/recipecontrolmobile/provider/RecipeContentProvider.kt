package com.example.recipecontrolmobile.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.recipecontrolmobile.model.Recipe
import com.example.recipecontrolmobile.model.RecipeRepository
import kotlinx.coroutines.runBlocking

class RecipeContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.recipecontrolmobile.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/recipes")

        private const val RECIPES = 1
        private const val RECIPE_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "recipes", RECIPES)
            addURI(AUTHORITY, "recipes/#", RECIPE_ID)
        }

        // Nombres de columnas
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_DESCRIPTION = "description"
        const val COL_INGREDIENTS = "ingredients"
        const val COL_NUTRITIONAL_INFO = "nutritional_info"
        const val COL_DAY = "day"
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val columns = arrayOf(COL_ID, COL_NAME, COL_DESCRIPTION, COL_INGREDIENTS, COL_NUTRITIONAL_INFO, COL_DAY)
        val cursor = MatrixCursor(columns)

        when (uriMatcher.match(uri)) {
            RECIPES -> {
                RecipeRepository.getAllRecipes().forEach { recipe ->
                    cursor.addRow(recipe.toRow())
                }
            }
            RECIPE_ID -> {
                val id = ContentUris.parseId(uri).toInt()
                RecipeRepository.getRecipeById(id)?.let { recipe ->
                    cursor.addRow(recipe.toRow())
                }
            }
        }

        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) != RECIPES || values == null) return null

        val id = (RecipeRepository.getAllRecipes().maxOfOrNull { it.id } ?: 0) + 1
        val recipe = Recipe(
            id = id,
            name = values.getAsString(COL_NAME) ?: "",
            description = values.getAsString(COL_DESCRIPTION) ?: "",
            ingredients = (values.getAsString(COL_INGREDIENTS) ?: "").split(","),
            nutritionalInfo = values.getAsString(COL_NUTRITIONAL_INFO) ?: "",
            day = values.getAsString(COL_DAY) ?: ""
        )
        runBlocking { RecipeRepository.addRecipe(recipe) }

        context?.contentResolver?.notifyChange(uri, null)
        return ContentUris.withAppendedId(CONTENT_URI, id.toLong())
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun getType(uri: Uri): String {
        return when (uriMatcher.match(uri)) {
            RECIPES -> "vnd.android.cursor.dir/vnd.$AUTHORITY.recipes"
            RECIPE_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.recipes"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    private fun Recipe.toRow(): Array<Any> {
        return arrayOf(id, name, description, ingredients.joinToString(","), nutritionalInfo, day)
    }
}
