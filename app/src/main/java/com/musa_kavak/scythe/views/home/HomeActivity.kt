package com.musa_kavak.scythe.views.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.musa_kavak.scythe.databinding.ActivityHomeBinding
import com.musa_kavak.scythe.services.BroadcastReceiverService
import com.musa_kavak.scythe.socket_io.RemoteFileManager
import com.musa_kavak.scythe.socket_io.SocketHandler

class HomeActivity : AppCompatActivity() {
    private lateinit var  bd : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bd.root)
        setListeners()

        startForegroundService(Intent(this,BroadcastReceiverService::class.java))
    }

    private fun setListeners(){

        bd.btnStop.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        bd.btnReconnect.setOnClickListener {
            SocketHandler.disconnectSocket()
            SocketHandler.getSocket(this)
        }
    }




}