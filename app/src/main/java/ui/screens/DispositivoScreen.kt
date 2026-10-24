package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiotecpreventivo.viewmodel.DispositivoViewModel

@Composable
fun DispositivoScreen(
    onContinuar: () -> Unit,
    viewModel: DispositivoViewModel = viewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registrar dispositivo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Ingresa información ficticia del dispositivo.")

        OutlinedTextField(
            value = state.nombre,
            onValueChange = viewModel::cambiarNombre,
            label = { Text("Nombre del dispositivo") },
            placeholder = { Text("Ejemplo: Mi teléfono") },
            isError = state.nombreError != null,
            trailingIcon = {
                if (state.nombreError != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en nombre",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                state.nombreError?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.tipo,
            onValueChange = viewModel::cambiarTipo,
            label = { Text("Tipo de dispositivo") },
            placeholder = { Text("Ejemplo: Teléfono") },
            isError = state.tipoError != null,
            trailingIcon = {
                if (state.tipoError != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en tipo",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                state.tipoError?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.sistemaOperativo,
            onValueChange = viewModel::cambiarSistemaOperativo,
            label = { Text("Sistema operativo") },
            placeholder = { Text("Ejemplo: Android") },
            isError = state.sistemaError != null,
            trailingIcon = {
                if (state.sistemaError != null) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = "Error en sistema operativo",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                state.sistemaError?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    onContinuar()
                }
            }
        ) {
            Text("Continuar a evaluación")
        }

        if (state.validado) {
            Text(
                text = "Dispositivo validado correctamente.",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}