package com.musa_kavak.scythe.models.notification

import android.app.PendingIntent
import android.graphics.drawable.Icon
import java.lang.reflect.Method

class NotificationAction(
    val title: String?,
    @Transient private val actionIntent: PendingIntent?
) {
    fun startAction() {
        actionIntent?.send()
    }
}