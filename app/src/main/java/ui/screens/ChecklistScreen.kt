package com.example.tiotecpreventivo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiotecpreventivo.viewmodel.EvaluacionViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tiotecpreventivo.RecordatorioWorker
import java.util.concurrent.TimeUnit

@Composable
fun ChecklistScreen(
    viewModel: EvaluacionViewModel,
    onVolver: () -> Unit
) {
    val acciones = viewModel.accionesPreventivas()
    val completadas = acciones.count { it.completada }
    val context = LocalContext.current
    val mensajeRecordatorio = remember { mutableStateOf("") }

    fun programarRecordatorio() {
        val trabajo = OneTimeWorkRequestBuilder<RecordatorioWorker>()
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "recordatorio_checklist",
            ExistingWorkPolicy.REPLACE,
            trabajo
        )
        mensajeRecordatorio.value = "Recordatorio programado para dentro de aproximadamente 1 hora"
    }

    val pedirPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permitido ->
        if (permitido) {
            programarRecordatorio()
        } else {
            mensajeRecordatorio.value = "Activa las notificaciones para recibir recordatorios"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Checklist preventivo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Acciones completadas: $completadas de ${acciones.size}")

        if (acciones.isEmpty()) {
            Text(
                text = "No tienes acciones pendientes. ¡Mantén tus buenos hábitos!",
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            acciones.forEach { accion ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = accion.completada,
                            onCheckedChange = { completada ->
                                viewModel.marcarAccionCompletada(
                                    indice = accion.indice,
                                    completada = completada
                                )
                            }
                        )

                        Text(
                            text = accion.texto,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
        if (acciones.isNotEmpty() && completadas < acciones.size) {
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        programarRecordatorio()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Recordarme revisar el checklist")
            }
        }

        if (mensajeRecordatorio.value.isNotEmpty()) {
            Text(mensajeRecordatorio.value)
        }
        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al resultado")
        }
    }
}