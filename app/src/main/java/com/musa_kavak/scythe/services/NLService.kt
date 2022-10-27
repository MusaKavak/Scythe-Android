package com.musa_kavak.scythe.services

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.musa_kavak.scythe.socket_io.NotificationManager
import com.musa_kavak.scythe.socket_io.SocketHandler

class NLService : NotificationListenerService() {
    private val tag = "NotificationService"
    private var notificationManager : NotificationManager? = null
    private var lastNotification : StatusBarNotification? = null
    override fun onBind(intent: Intent): IBinder {
        return super.onBind(intent)!!
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        super.onNotificationPosted(sbn, rankingMap)
        sbn?.let {
            if(lastNotification != it)
            notificationManager?.newNotification(it)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?, rankingMap: RankingMap?) {
        sbn?.let {
            notificationManager?.removeNotification(it)
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.e(tag, "Listener Connected")
        SocketHandler.getSocket(applicationContext)
        if(notificationManager==null){
            notificationManager = NotificationManager(applicationContext)
        }

        val manager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.addPrimaryClipChangedListener {  }
        manager.addPrimaryClipChangedListener {
            val copiedItem = manager.primaryClip
            println(copiedItem?.itemCount)
            println(copiedItem?.getItemAt(0))
        }
    }


    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        SocketHandler.disconnectSocket()
    }
}