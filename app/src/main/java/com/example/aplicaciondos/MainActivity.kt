package com.example.aplicaciondos


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    data class Recipe(
        val name: String,
        val description: String,
        val time: String,
        val ingredients: List<String>,
        val steps: List<String>,
        val imageRes: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipes = listOf(
            Recipe(
                "Tacos Rápidos",
                "Deliciosos tacos en 10 minutos",
                "10 min",
                listOf("Tortillas", "Carne", "Cebolla", "Cilantro"),
                listOf("Calienta la tortilla", "Agrega la carne", "Sirve con cebolla y cilantro"),
                R.imagen1
            ),
            Recipe(
                "Ensalada Fresca",
                "Ensalada saludable con ingredientes frescos",
                "15 min",
                listOf("Lechuga", "Tomate", "Pepino", "Aceite de oliva"),
                listOf("Lava los vegetales", "Corta en trozos", "Mezcla y sirve"),
                R.imagen2
            ),
            Recipe(
                "Smoothie Energético",
                "Batido de frutas para empezar el día",
                "5 min",
                listOf("Banana", "Fresas", "Leche", "Miel"),
                listOf("Coloca todo en la licuadora", "Licúa hasta quedar homogéneo", "Sirve frío"),
                R.imagen3 
            ),
            Recipe(
                "Tacos Rápidos",
                "Deliciosos tacos en 10 minutos",
                "10 min",
                listOf("Tortillas", "Carne", "Cebolla", "Cilantro"),
                listOf("Calienta la tortilla", "Agrega la carne", "Sirve con cebolla y cilantro"),
                R.imagen1
            ),
            Recipe(
                "Ensalada Fresca",
                "Ensalada saludable con ingredientes frescos",
                "15 min",
                listOf("Lechuga", "Tomate", "Pepino", "Aceite de oliva"),
                listOf("Lava los vegetales", "Corta en trozos", "Mezcla y sirve"),
                R.imagen2
            ),
            Recipe(
                "Smoothie Energético",
                "Batido de frutas para empezar el día",
                "5 min",
                listOf("Banana", "Fresas", "Leche", "Miel"),
                listOf("Coloca todo en la licuadora", "Licúa hasta quedar homogéneo", "Sirve frío"),
                R.imagen3
            )
        )

        setContent {
            RecipeApp(recipes)
        }
    }

    @Composable
    fun RecipeApp(recipes: List<Recipe>) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "list") {
            composable("list") { RecipeListScreen(recipes, navController) }
            composable(
                "detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { backStackEntry ->
                val index = backStackEntry.arguments?.getInt("index") ?: 0
                RecipeDetailScreen(recipes[index], navController)
            }
        }
    }

    @Composable
    fun RecipeListScreen(recipes: List<Recipe>, navController: NavHostController) {
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar receta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            val filteredRecipes = recipes.filter {
                it.name.contains(searchQuery.text, ignoreCase = true)
            }

            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(filteredRecipes) { recipe ->
                    RecipeCard(recipe) {
                        val index = recipes.indexOf(recipe)
                        navController.navigate("detail/$index")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    @Composable
    fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = recipe.imageRes),
                    contentDescription = recipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
                Text(recipe.time, style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    @Composable
    fun RecipeDetailScreen(recipe: Recipe, navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = recipe.imageRes),
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(recipe.name, style = MaterialTheme.typography.titleLarge)
            Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
            Text("Tiempo: ${recipe.time}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(12.dp))
            Text("Ingredientes:", style = MaterialTheme.typography.titleMedium)
            recipe.ingredients.forEach { ingredient ->
                Text("- $ingredient", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Pasos:", style = MaterialTheme.typography.titleMedium)
            recipe.steps.forEachIndexed { index, step ->
                Text("${index + 1}. $step", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

