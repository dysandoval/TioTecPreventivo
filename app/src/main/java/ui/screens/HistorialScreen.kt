package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiotecpreventivo.viewmodel.HistorialViewModel
import com.example.tiotecpreventivo.data.local.HistorialEvaluacionEntity

@Composable
fun HistorialScreen(
    onVolver: () -> Unit,
    historialViewModel: HistorialViewModel = viewModel()
) {
    val historial = historialViewModel.historial.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Historial de evaluaciones",
            style = MaterialTheme.typography.headlineMedium
        )

        if (historial.isEmpty()) {
            Text(
                text = "Aún no hay evaluaciones guardadas.",
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            historial.forEach { evaluacion ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = evaluacion.nivelRiesgo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text("Fecha: ${evaluacion.fecha}")
                        Text(
                            "Respuestas seguras: " +
                                    "${evaluacion.respuestasSeguras} de 6"
                        )
                        Text(
                            "Checklist: ${evaluacion.accionesCompletadas} " +
                                    "de ${evaluacion.accionesTotales} acciones completadas"
                        )
                        Text(
                            text = "Recomendaciones:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(evaluacion.recomendaciones)
                    }
                }
            }
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}