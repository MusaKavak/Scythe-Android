package com.musa_kavak.scythe.socket_io

import android.content.Context
import io.socket.client.Socket

class SocketListener {
    companion object {
        private var _socket: Socket? = null

        fun initializeListeners(context: Context, socket: Socket) {
            _socket = socket

            val mediaSessionManager = MediaSessionManager(context)
            val notificationManager = NotificationManager
            val remoteFileManager = RemoteFileManager(context)

            socket.on("AndroidMediaSessionRequest") {
                mediaSessionManager.updateMediaSessions()
            }
            socket.on("AndroidMediaSessionControl") {
                mediaSessionManager.sendActionToMediaSession(it[0] as String, it[1] as Int)
            }
            socket.on("AndroidCurrentNotificationsRequest") {
                notificationManager.sendCurrentNotificationList(context)
            }
            socket.on("AndroidNotificationActionControl"){
                notificationManager.activateNotificationAction(it[0] as String, it[1] as String)
            }
            socket.on("AndroidInternalStorageRequest"){
                remoteFileManager.sendInternalStorage()
            }
            socket.on("AndroidFileRequest"){
                remoteFileManager.sendFile(it[0] as String)
            }

        }

        fun disbandListeners() {
            _socket?.let {
                it.off("AndroidMediaSessionRequest")
                it.off("AndroidMediaSessionControl")
                it.off("AndroidCurrentNotificationsRequest")
                it.off("AndroidNotificationActionControl")
                it.off("AndroidInternalStorageRequest")
                it.off("AndroidFileRequest")
            }
            _socket = null
        }
    }
}
