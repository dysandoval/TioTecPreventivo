package com.example.tiotecpreventivo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tiotecpreventivo.ui.screens.ChecklistScreen
import com.example.tiotecpreventivo.ui.screens.DispositivoScreen
import com.example.tiotecpreventivo.ui.screens.EvaluacionScreen
import com.example.tiotecpreventivo.ui.screens.HistorialScreen
import com.example.tiotecpreventivo.ui.screens.InicioScreen
import com.example.tiotecpreventivo.ui.screens.PerfilScreen
import com.example.tiotecpreventivo.ui.screens.ResultadoScreen
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val perfilViewModel: PerfilViewModel = viewModel()
    val dispositivoViewModel: DispositivoViewModel = viewModel()
    val evaluacionViewModel: EvaluacionViewModel = viewModel()

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
                viewModel = perfilViewModel,
                onContinuar = {
                    navController.navigate("dispositivo")
                }
            )
        }

        composable("dispositivo") {
            DispositivoScreen(
                viewModel = dispositivoViewModel,
                onContinuar = {
                    navController.navigate("evaluacion")
                }
            )
        }

        composable("evaluacion") {
            EvaluacionScreen(
                viewModel = evaluacionViewModel,
                onFinalizar = {
                    navController.navigate("resultado")
                }
            )
        }

        composable("resultado") {
            ResultadoScreen(
                viewModel = evaluacionViewModel,
                onAbrirChecklist = {
                    navController.navigate("checklist")
                },
                onAbrirHistorial = {
                    navController.navigate("historial")
                },
                onVolverAlInicio = {
                    navController.navigate("inicio") {
                        popUpTo("inicio") {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("checklist") {
            ChecklistScreen(
                viewModel = evaluacionViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("historial") {
            HistorialScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}