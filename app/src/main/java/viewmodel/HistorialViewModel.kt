package com.example.tiotecpreventivo.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiotecpreventivo.data.local.AppDatabase
import com.example.tiotecpreventivo.data.local.HistorialEvaluacionEntity
import com.example.tiotecpreventivo.data.local.HistorialRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistorialViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HistorialRepository(
        AppDatabase.obtenerInstancia(application).historialEvaluacionDao()
    )

    val historial: StateFlow<List<HistorialEvaluacionEntity>> =
        repository.obtenerHistorial().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    var guardando by mutableStateOf(false)
        private set

    var guardado by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun guardarEvaluacion(
        nivelRiesgo: String,
        respuestasSeguras: Int,
        recomendaciones: List<String>,
        accionesCompletadas: Int,
        accionesTotales: Int
    ) {
        if (guardando || guardado) return

        viewModelScope.launch {
            guardando = true
            error = null

            try {
                val fecha = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                ).format(Date())

                repository.guardar(
                    HistorialEvaluacionEntity(
                        fecha = fecha,
                        nivelRiesgo = nivelRiesgo,
                        respuestasSeguras = respuestasSeguras,
                        recomendaciones = recomendaciones.joinToString("\n"),
                        accionesCompletadas = accionesCompletadas,
                        accionesTotales = accionesTotales
                    )
                )

                guardado = true
            } catch (e: Exception) {
                error = "No se pudo guardar la evaluación."
            } finally {
                guardando = false
            }
        }
    }
}