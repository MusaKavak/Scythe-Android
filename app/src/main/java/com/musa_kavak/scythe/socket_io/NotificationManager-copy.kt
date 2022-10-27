package com.musa_kavak.scythe.socket_io

import android.content.Context
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.util.Log

class `NotificationManager-copy`(val context: Context) {
    private val tag = "NotificationManager"
    fun newNotification(sbn: StatusBarNotification) {
        val map = getBundleMap(sbn.notification.extras)
        Log.e(tag, "//////////////////////////////////////////// ")
        map.forEach {
            Log.i(tag, "${it.key}   ------>   ${it.value}")
        }
        Log.e(tag, "//////////////////")
        sbn.notification.actions?.forEach {
            Log.e(tag, "------------------${it.title}")
            val newMap = getBundleMap(it.extras)
            newMap.forEach { mapEntry ->
                Log.i(tag, "${mapEntry.key}   ------>   ${mapEntry.value}")
            }
            Log.e(tag, "-----------------------------")
        }
        Log.e(tag, "////////////////////////////////////////////")
    }

    private fun getBundleMap(bundle: Bundle): HashMap<String, Any> {
        val map = hashMapOf<String, Any>()
        bundle.keySet().forEach {
            val key = it
            val value = bundle.get(it)
            Log.i(tag, "${key}   ------>   ${value} ------> ${value?.javaClass?.typeName}")
            value?.let { vl ->

            }
        }
        return map
    }
}
