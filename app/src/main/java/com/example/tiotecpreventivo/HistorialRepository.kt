package com.example.tiotecpreventivo.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HistorialRepository(
    private val historialEvaluacionDao: `HistorialEvaluacionDao`
) {

    fun obtenerHistorial(): Flow<List<HistorialEvaluacionEntity>> {
        return historialEvaluacionDao.obtenerTodo()
    }

    suspend fun guardar(evaluacion: HistorialEvaluacionEntity) {
        withContext(Dispatchers.IO) {
            historialEvaluacionDao.insertar(evaluacion)
        }
    }
}