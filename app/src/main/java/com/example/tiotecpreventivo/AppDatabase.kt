package com.example.tiotecpreventivo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [HistorialEvaluacionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historialEvaluacionDao(): HistorialEvaluacionDao

    companion object {
        @Volatile
        private var instancia: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tiotec_preventivo_database"
                ).build().also { baseDeDatos ->
                    instancia = baseDeDatos
                }
            }
        }
    }
}