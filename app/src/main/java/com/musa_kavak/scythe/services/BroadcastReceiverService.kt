package com.musa_kavak.scythe.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.*
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.os.IBinder
import androidx.core.content.getSystemService

class BroadcastReceiverService:Service() {
    private val notificationChannelId = "Scythe Broadcast Receiver Service"
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel = NotificationChannel(
            notificationChannelId,
            notificationChannelId,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService<NotificationManager>()?.createNotificationChannel(channel)

        val notification = Notification.Builder(this,notificationChannelId).setContentTitle("Scythe Broadcast Receiver Running.").build()
        startForeground(1888,notification)


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}