package org.terciolab.wiktionaryapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.terciolab.wiktionaryapp.meanings.MeaningsView
import org.terciolab.wiktionaryapp.search.SearchView



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Nav.Search.route) {
        composable(Nav.Search.route) {
            SearchView(navController)
        }
        composable(
            Nav.Details.route,
            arguments = listOf(
                navArgument("lang") { type = NavType.StringType },
                navArgument("word") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val word = backStackEntry.arguments?.getString("word") ?: ""
            val lang = backStackEntry.arguments?.getString("lang") ?: "en"
            MeaningsView(word, lang)
        }
    }

}

sealed class Nav(val route: String ) {
    object Search           : Nav("search")
    object Details          : Nav("details/{lang}/{word}")
}