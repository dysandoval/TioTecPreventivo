package com.example.tiotecpreventivo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Pregunta(val texto: String)

data class AccionPreventiva(
    val indice: Int,
    val texto: String,
    val completada: Boolean
)

data class EvaluacionUiState(
    val indiceActual: Int = 0,
    val respuestas: List<Boolean?> = List(6) { null },
    val accionesCompletadas: List<Boolean> = List(6) { false },
    val errorRespuesta: String? = null,
    val finalizada: Boolean = false
)

class EvaluacionViewModel : ViewModel() {

    val preguntas = listOf(
        Pregunta("¿Mantienes activadas las actualizaciones automáticas?"),
        Pregunta("¿Realizas respaldos periódicos?"),
        Pregunta("¿Usas autenticación de dos factores?"),
        Pregunta("¿Tienes configurado un método de recuperación de cuenta?"),
        Pregunta("¿Evitas reutilizar contraseñas?"),
        Pregunta("¿Mantienes actualizado el sistema operativo?")
    )

    private val recomendacionesPorPregunta = listOf(
        "Activa las actualizaciones automáticas de tus aplicaciones y sistema.",
        "Realiza copias de seguridad periódicas de tu información importante.",
        "Activa la autenticación de dos factores en tus cuentas principales.",
        "Configura un correo o teléfono de recuperación para tus cuentas.",
        "Utiliza contraseñas distintas y seguras para cada cuenta.",
        "Mantén actualizado el sistema operativo de tu dispositivo."
    )

    var uiState by mutableStateOf(EvaluacionUiState())
        private set

    fun responder(respuestaSegura: Boolean) {
        val respuestasActualizadas = uiState.respuestas.toMutableList()
        respuestasActualizadas[uiState.indiceActual] = respuestaSegura

        uiState = uiState.copy(
            respuestas = respuestasActualizadas,
            errorRespuesta = null
        )
    }

    fun siguiente(): Boolean {
        if (uiState.respuestas[uiState.indiceActual] == null) {
            uiState = uiState.copy(
                errorRespuesta = "Selecciona Sí o No para continuar."
            )
            return false
        }

        if (uiState.indiceActual < preguntas.lastIndex) {
            uiState = uiState.copy(
                indiceActual = uiState.indiceActual + 1,
                errorRespuesta = null
            )
            return false
        }

        uiState = uiState.copy(
            finalizada = true,
            errorRespuesta = null
        )
        return true
    }

    fun respuestasSeguras(): Int {
        return uiState.respuestas.count { it == true }
    }

    fun nivelRiesgo(): String {
        return when (respuestasSeguras()) {
            in 0..2 -> "Riesgo alto"
            in 3..4 -> "Riesgo medio"
            else -> "Riesgo bajo"
        }
    }

    fun recomendaciones(): List<String> {
        val recomendaciones = uiState.respuestas.mapIndexedNotNull { indice, respuesta ->
            if (respuesta == false) recomendacionesPorPregunta[indice] else null
        }

        return if (recomendaciones.isEmpty()) {
            listOf("Mantén estos hábitos de seguridad digital.")
        } else {
            recomendaciones
        }
    }

    fun accionesPreventivas(): List<AccionPreventiva> {
        return uiState.respuestas.mapIndexedNotNull { indice, respuesta ->
            if (respuesta == false) {
                AccionPreventiva(
                    indice = indice,
                    texto = recomendacionesPorPregunta[indice],
                    completada = uiState.accionesCompletadas[indice]
                )
            } else {
                null
            }
        }
    }

    fun marcarAccionCompletada(indice: Int, completada: Boolean) {
        val accionesActualizadas = uiState.accionesCompletadas.toMutableList()
        accionesActualizadas[indice] = completada

        uiState = uiState.copy(
            accionesCompletadas = accionesActualizadas
        )
    }

    fun reiniciar() {
        uiState = EvaluacionUiState()
    }
}