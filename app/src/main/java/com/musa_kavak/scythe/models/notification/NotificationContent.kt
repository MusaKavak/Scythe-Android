package com.musa_kavak.scythe.models.notification

data class NotificationContent (
    val appIcon : String?,
    val appName: String?,
    val packageName: String?,
    val title: String?,
    val text: String?,
    val largeIcon: String?,
    val actionList: List<NotificationAction>?,
    val key: String?
)
