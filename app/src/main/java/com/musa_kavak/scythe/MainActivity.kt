package com.musa_kavak.scythe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*

class MainActivity : AppCompatActivity() {
    private lateinit var mClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mClient = OkHttpClient()
        startSocket()
    }

    private fun startSocket(){
        Log.i("WebsocketListener", "Opened-----------------")
        val request: Request = Request.Builder().url("http://192.168.1.112:8080").build()

        val listener = ScytheWebSocketListener()
        val webSocket = mClient.newWebSocket(request,listener);
        mClient.dispatcher().executorService().shutdown()
    }
}

class ScytheWebSocketListener: WebSocketListener(){
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)

        Log.i("WebsocketListener", "Opened")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.i("WebsocketListener", t.message!!)
    }
}