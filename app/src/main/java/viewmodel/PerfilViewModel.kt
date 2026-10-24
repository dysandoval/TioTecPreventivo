package com.example.tiotecpreventivo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class PerfilUiState(
    val nombre: String = "",
    val nivelExperiencia: String = "",
    val nombreError: String? = null,
    val nivelError: String? = null
)

class PerfilViewModel : ViewModel() {

    var uiState by mutableStateOf(PerfilUiState())
        private set

    fun cambiarNombre(valor: String) {
        uiState = uiState.copy(
            nombre = valor,
            nombreError = null
        )
    }

    fun cambiarNivel(valor: String) {
        uiState = uiState.copy(
            nivelExperiencia = valor,
            nivelError = null
        )
    }

    fun validarFormulario(): Boolean {
        val errorNombre =
            if (uiState.nombre.trim().length < 3) {
                "Ingresa un nombre ficticio de al menos 3 letras."
            } else {
                null
            }

        val errorNivel =
            if (uiState.nivelExperiencia.isBlank()) {
                "Indica tu nivel de experiencia digital."
            } else {
                null
            }

        uiState = uiState.copy(
            nombreError = errorNombre,
            nivelError = errorNivel
        )

        return errorNombre == null && errorNivel == null
    }
}