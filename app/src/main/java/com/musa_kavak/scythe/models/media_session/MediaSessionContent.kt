package com.musa_kavak.scythe.models.media_session

import android.app.PendingIntent
import android.graphics.Bitmap
import android.media.session.MediaController

data class MediaSessionContent(
    val packageName:String,
    val albumArt: Bitmap?,
    val artist : String?,
    val duration : Long?,
    val album: String?,
    val title:String?,
    val controls : MediaController.TransportControls
)
