package com.example.tiotecpreventivo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class DispositivoUiState(
    val nombre: String = "",
    val tipo: String = "",
    val sistemaOperativo: String = "",
    val nombreError: String? = null,
    val tipoError: String? = null,
    val sistemaError: String? = null,
    val validado: Boolean = false
)

class DispositivoViewModel : ViewModel() {

    var uiState by mutableStateOf(DispositivoUiState())
        private set

    fun cambiarNombre(nombre: String) {
        uiState = uiState.copy(
            nombre = nombre,
            nombreError = null,
            validado = false
        )
    }

    fun cambiarTipo(tipo: String) {
        uiState = uiState.copy(
            tipo = tipo,
            tipoError = null,
            validado = false
        )
    }

    fun cambiarSistemaOperativo(sistemaOperativo: String) {
        uiState = uiState.copy(
            sistemaOperativo = sistemaOperativo,
            sistemaError = null,
            validado = false
        )
    }

    fun validarFormulario(): Boolean {
        val errorNombre =
            if (uiState.nombre.trim().length < 3) {
                "Ingresa un nombre de al menos 3 letras."
            } else {
                null
            }

        val errorTipo =
            if (uiState.tipo.isBlank()) {
                "Indica el tipo de dispositivo."
            } else {
                null
            }

        val errorSistema =
            if (uiState.sistemaOperativo.isBlank()) {
                "Indica el sistema operativo."
            } else {
                null
            }

        val esValido = errorNombre == null &&
                errorTipo == null &&
                errorSistema == null

        uiState = uiState.copy(
            nombreError = errorNombre,
            tipoError = errorTipo,
            sistemaError = errorSistema,
            validado = esValido
        )

        return esValido
    }
}