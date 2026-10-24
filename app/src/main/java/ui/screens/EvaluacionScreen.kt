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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun EvaluacionScreen(
    viewModel: EvaluacionViewModel = viewModel()
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

        Text(
            text = "Pregunta ${state.indiceActual + 1} de ${viewModel.preguntas.size}"
        )

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
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (respuestaActual == true) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text("Sí")
            }

            Button(
                onClick = { viewModel.responder(false) },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (respuestaActual == false) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                ),
                modifier = Modifier.weight(1f)
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

        if (!state.finalizada) {
            Button(
                onClick = viewModel::siguiente,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (state.indiceActual == viewModel.preguntas.lastIndex) {
                        "Finalizar evaluación"
                    } else {
                        "Siguiente"
                    }
                )
            }
        } else {
            Text(
                text = "Evaluación completada correctamente.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}