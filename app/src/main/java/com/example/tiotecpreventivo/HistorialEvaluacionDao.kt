package com.example.tiotecpreventivo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialEvaluacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(evaluacion: HistorialEvaluacionEntity)

    @Query("SELECT * FROM historial_evaluaciones ORDER BY id DESC")
    fun obtenerTodo(): Flow<List<HistorialEvaluacionEntity>>
}