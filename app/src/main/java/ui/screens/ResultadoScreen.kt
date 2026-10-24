package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel

@Composable
fun ResultadoScreen(
    viewModel: EvaluacionViewModel,
    onVolverAlInicio: () -> Unit
) {
    val nivel = viewModel.nivelRiesgo()
    val respuestasSeguras = viewModel.respuestasSeguras()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Resultado preventivo",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = nivel,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (nivel == "Riesgo alto") {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )

                Text(
                    text = "Respuestas seguras: $respuestasSeguras de 6"
                )
            }
        }

        Text(
            text = "Recomendaciones",
            style = MaterialTheme.typography.titleLarge
        )

        viewModel.recomendaciones().forEach { recomendacion ->
            Text("• $recomendacion")
        }

        Button(
            onClick = {
                viewModel.reiniciar()
                onVolverAlInicio()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}