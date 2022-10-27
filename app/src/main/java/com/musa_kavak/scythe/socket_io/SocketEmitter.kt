package com.musa_kavak.scythe.socket_io

import android.content.Context
import com.google.gson.Gson
import com.musa_kavak.scythe.models.EmitObject
import com.musa_kavak.scythe.services.UserService
import io.socket.client.Socket

class SocketEmitter(context: Context) {
    private var userId: Int? = null
    private var socket: Socket? = null

    init {
        userId = UserService.getCurrentUser(context)?.id
        socket = SocketHandler.getSocket(context)
    }

    fun AndroidMediaSessionResponse(data: Any?) {
        dataToJsonString(data)?.let {
            emit("AndroidMediaSessionResponse", it)
        }
    }

    fun AndroidCurrentNotificationsResponse(data: Any?) {
        dataToJsonString(data)?.let {
            emit("AndroidCurrentNotificationsResponse", it)
        }
    }

    fun AndroidNewNotification(data: Any?) {
        dataToJsonString(data)?.let {
            emit("AndroidNewNotification", it)
        }
    }

    fun AndroidInternalStorageResponse(data: Any?) {
        dataToJsonString(data)?.let {
            emit("AndroidInternalStorageResponse", it)
        }
    }

    fun AndroidFileResponse(data: String?) {
        emit("AndroidFileResponse", data)
    }

    private fun emit(event: String, data: String?) {
        socket?.emit(event, data, userId)
    }

    private fun dataToJsonString(data: Any?): String? {
        return if (
            data != null
        ) {
            Gson().toJson(
                data
            ).toString()
        } else null
    }
}