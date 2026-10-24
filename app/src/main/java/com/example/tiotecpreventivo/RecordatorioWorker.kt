package com.example.tiotecpreventivo

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class RecordatorioWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.success()
        }

        val intent = Intent(applicationContext, MainActivity::class.java)
        val abrirApp = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(
            applicationContext,
            "recordatorios"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("TíoTec Preventivo")
            .setContentText("Revisa las acciones pendientes de tu checklist.")
            .setContentIntent(abrirApp)
            .setAutoCancel(true)
            .build()

        val administrador = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as android.app.NotificationManager

        administrador.notify(1, notificacion)
        return Result.success()
    }
}