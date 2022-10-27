package com.musa_kavak.scythe.models.media_session

import android.graphics.Bitmap

data class MediaSessionResponse(
    val packageName:String,
    val albumArt: String?,
    val artist : String?,
    val duration : Long?,
    val album: String?,
    val title:String?
)
