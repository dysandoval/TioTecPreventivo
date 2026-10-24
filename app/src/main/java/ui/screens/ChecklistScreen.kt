package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun ChecklistScreen(
    viewModel: EvaluacionViewModel,
    onVolver: () -> Unit
) {
    val acciones = viewModel.accionesPreventivas()
    val completadas = acciones.count { it.completada }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Checklist preventivo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Acciones completadas: $completadas de ${acciones.size}")

        if (acciones.isEmpty()) {
            Text(
                text = "No tienes acciones pendientes. ¡Mantén tus buenos hábitos!",
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            acciones.forEach { accion ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = accion.completada,
                            onCheckedChange = { completada ->
                                viewModel.marcarAccionCompletada(
                                    indice = accion.indice,
                                    completada = completada
                                )
                            }
                        )

                        Text(
                            text = accion.texto,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al resultado")
        }
    }
}