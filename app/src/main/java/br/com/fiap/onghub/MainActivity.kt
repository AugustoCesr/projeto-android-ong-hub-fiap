package br.com.fiap.onghub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.onghub.screens.DetalhesOrganizacoesScreen
import br.com.fiap.onghub.screens.DicasDeVoluntarioScreen
import br.com.fiap.onghub.screens.HomeScreen
import br.com.fiap.onghub.screens.MapaOngScreen
import br.com.fiap.onghub.ui.theme.ONGHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ONGHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable(route = "home") {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                contentPadding = innerPadding
                            )
                        }
                        composable(
                            route = "detalhesOrganizacoes/{ongId}",
                            arguments = listOf(navArgument("ongId") { type = NavType.StringType })
                            ) {
                            DetalhesOrganizacoesScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("dicasVoluntariado") {
                            DicasDeVoluntarioScreen(navController)
                        }
                        composable(
                            route = "mapaOng?name={name}&address={address}&lat={lat}&lng={lng}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType; defaultValue = "" },
                                navArgument("address") { type = NavType.StringType; nullable = true; defaultValue = null },
                                navArgument("lat") { type = NavType.StringType; nullable = true; defaultValue = null },
                                navArgument("lng") { type = NavType.StringType; nullable = true; defaultValue = null },
                            )
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name").orEmpty()
                            val address = backStackEntry.arguments?.getString("address")
                            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull()
                            val lng = backStackEntry.arguments?.getString("lng")?.toDoubleOrNull()
                            MapaOngScreen(
                                navController = navController,
                                name = name,
                                address = address,
                                latArg = lat,
                                lngArg = lng
                            )
                        }

                    }
                }
            }
        }
    }
}

