package com.example.tiotecpreventivo.ui.navigation

import com.example.tiotecpreventivo.ui.screens.DispositivoScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            InicioScreen(
                onComenzar = {
                    navController.navigate("perfil")
                }
            )
        }

        composable("perfil") {
            PerfilScreen(
                onContinuar = {
                    navController.navigate("dispositivo")
                }
            )
        }

        composable("dispositivo") {
            DispositivoScreen()
        }
    }
}