package com.musa_kavak.scythe.socket_io

import android.app.Notification
import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.service.notification.StatusBarNotification
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import com.musa_kavak.scythe.models.notification.NotificationAction
import com.musa_kavak.scythe.models.notification.NotificationContent
import java.io.ByteArrayOutputStream

class NotificationManager(private val context: Context) {
    companion object {
        private var socketEmitter : SocketEmitter? = null
        private fun socketEmitter(context: Context):SocketEmitter{
            return if (socketEmitter == null){
                socketEmitter = SocketEmitter(context )
                socketEmitter!!
            }else
                socketEmitter!!
        }
        val notificationList = mutableListOf<NotificationContent>()
        fun sendCurrentNotificationList(context: Context){
            socketEmitter(context).AndroidCurrentNotificationsResponse(notificationList)
        }
        fun activateNotificationAction(notificationKey:String,actionTitle:String){
            val notification = notificationList.find {
                it.key == notificationKey
            }
            val action = notification?.actionList?.find {
                it.title == actionTitle
            }
            action?.startAction()
        }
    }

    private var lastNotification : NotificationContent? = null
    private var socketEmitter :SocketEmitter? = null
    fun newNotification(sbn: StatusBarNotification) {
        val index = notificationList.indexOfFirst {
            it.key == sbn.key
        }
        val newNotificationContent = createNewNotificationContentObject(sbn)
        if (index == -1) {
            notificationList.add(newNotificationContent)
        } else {
            notificationList[index] = newNotificationContent
        }
        val condition =
            lastNotification?.title == newNotificationContent.title && lastNotification?.text == newNotificationContent.text
        if(!condition){
            if (socketEmitter == null) socketEmitter = SocketEmitter(context)
            socketEmitter!!.AndroidNewNotification(newNotificationContent)
            lastNotification = newNotificationContent
        }
    }

    fun removeNotification(sbn: StatusBarNotification) {
        notificationList.removeIf {
            it.key == sbn.key
        }
    }

    private fun createNewNotificationContentObject(sbn: StatusBarNotification): NotificationContent {
        val appInfo = sbn.notification.extras.get("android.appInfo") as ApplicationInfo
        val icon = sbn.notification.extras.get("android.largeIcon") as Icon?
        val iconString = bitmapToString(icon?.loadDrawable(context)?.toBitmap())
        val appName = appInfo.loadLabel(context.packageManager).toString()
        val appIcon = bitmapToString(appInfo.loadIcon(context.packageManager).toBitmapOrNull())
        return NotificationContent(
            appIcon,
            appName,
            appInfo.packageName,
            controlString(sbn.notification.extras.get("android.title")),
            controlString(sbn.notification.extras.get("android.text")),
            iconString,
            getNotificationActionList(sbn.notification.actions),
            sbn.key
        )
    }

    private fun getNotificationActionList(actions: Array<Notification.Action>?): List<NotificationAction>? {
        actions?.let {
            val newActionList = mutableListOf<NotificationAction>()
            actions.forEach {
                newActionList.add(
                    NotificationAction(
                        it.title.toString(),
                        it.actionIntent
                    )
                )
            }
            return newActionList
        }
        return null
    }

    private fun controlString(item: Any?): String? {
        if (item != null && item.toString().isNotEmpty()) {
            return item.toString()
        }
        return null
    }

    private fun bitmapToString(bit: Bitmap?): String? {
        bit?.let {
            val stream = ByteArrayOutputStream()

            it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val imageBytes = stream.toByteArray()
            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }
        return null
    }
}
