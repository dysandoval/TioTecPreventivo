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
import com.example.tiotecpreventivo.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = viewModel()
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
            text = "Crear perfil ficticio",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Completa los datos de prueba para continuar.")

        OutlinedTextField(
            value = state.nombre,
            onValueChange = viewModel::cambiarNombre,
            label = { Text("Nombre ficticio") },
            singleLine = true,
            isError = state.nombreError != null,
            trailingIcon = {
                if (state.nombreError != null) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error en nombre",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                state.nombreError?.let { error ->
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.nivelExperiencia,
            onValueChange = viewModel::cambiarNivel,
            label = { Text("Nivel de experiencia digital") },
            placeholder = { Text("Ejemplo: Básico") },
            singleLine = true,
            isError = state.nivelError != null,
            trailingIcon = {
                if (state.nivelError != null) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error en nivel",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                state.nivelError?.let { error ->
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.validarFormulario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar perfil")
        }
    }
}