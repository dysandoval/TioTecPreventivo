package com.example.tiotecpreventivo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_evaluaciones")
data class HistorialEvaluacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fecha: String,
    val nivelRiesgo: String,
    val respuestasSeguras: Int,
    val recomendaciones: String,
    val accionesCompletadas: Int,
    val accionesTotales: Int
)