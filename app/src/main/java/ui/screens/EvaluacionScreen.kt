package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun EvaluacionScreen(
    viewModel: EvaluacionViewModel,
    onFinalizar: () -> Unit
) {
    val state = viewModel.uiState
    val respuestaActual = state.respuestas[state.indiceActual]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Evaluación preventiva",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Pregunta ${state.indiceActual + 1} de ${viewModel.preguntas.size}")

        Text(
            text = viewModel.preguntas[state.indiceActual].texto,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.responder(true) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (respuestaActual == true) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                )
            ) {
                Text("Sí")
            }

            Button(
                onClick = { viewModel.responder(false) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (respuestaActual == false) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                )
            ) {
                Text("No")
            }
        }

        state.errorRespuesta?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                if (viewModel.siguiente()) {
                    onFinalizar()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (state.indiceActual == viewModel.preguntas.lastIndex) {
                    "Ver resultado"
                } else {
                    "Siguiente"
                }
            )
        }
    }
}