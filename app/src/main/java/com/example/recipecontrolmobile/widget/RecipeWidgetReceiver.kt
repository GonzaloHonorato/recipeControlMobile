package com.example.recipecontrolmobile.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.recipecontrolmobile.HomeActivity
import com.example.recipecontrolmobile.R
import com.example.recipecontrolmobile.model.RecipeRepository

class RecipeWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val recipes = RecipeRepository.getAllRecipes()
            val recipe = if (recipes.isNotEmpty()) recipes.random() else null

            val views = RemoteViews(context.packageName, R.layout.widget_recipe_loading)

            if (recipe != null) {
                views.setTextViewText(R.id.widget_recipe_name, recipe.name)
                views.setTextViewText(R.id.widget_recipe_description, recipe.description)
                views.setTextViewText(R.id.widget_recipe_info, recipe.nutritionalInfo)
            } else {
                views.setTextViewText(R.id.widget_recipe_name, "NutriControl")
                views.setTextViewText(R.id.widget_recipe_description, "Abre la app para ver recetas")
                views.setTextViewText(R.id.widget_recipe_info, "")
            }

            // Click en el widget abre la app
            val intent = Intent(context, HomeActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
