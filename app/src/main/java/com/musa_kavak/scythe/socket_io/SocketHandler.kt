package com.musa_kavak.scythe.socket_io

import android.content.Context
import android.util.Log
import com.musa_kavak.scythe.services.UserService
import io.socket.client.IO
import io.socket.client.Socket

class SocketHandler {
    companion object {
        private var mSocket: Socket? = null

        fun getSocket(context: Context): Socket? {
            if (mSocket == null) {
                val user = UserService.getCurrentUser(context)
                if (user == null) {
                    mSocket = null
                } else {
                    val newSocket = IO.socket("https://scythe-websocket-server.herokuapp.com/").connect()
                    newSocket.on(Socket.EVENT_CONNECT_ERROR) {
                        Log.e("SocketConnectionError", "Some Error Occurred: ${it[0]}")
                        mSocket = null
                    }

                    newSocket.on(Socket.EVENT_CONNECT) {
                        newSocket.emit(
                            "JoinRoom",
                            user.id.toString(),
                            "Android"
                        )
                        SocketListener.disbandListeners()
                        mSocket = newSocket
                        SocketListener.initializeListeners(context, mSocket!!)
                    }
                }
            }
            return mSocket
        }

        fun disconnectSocket(){
            mSocket?.disconnect()
            SocketListener.disbandListeners()
            mSocket = null
        }
    }
}