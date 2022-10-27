package com.musa_kavak.scythe.socket_io

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaSessionManager
import android.provider.Settings
import android.util.Base64
import androidx.core.content.getSystemService
import com.musa_kavak.scythe.models.media_session.MediaSessionContent
import com.musa_kavak.scythe.models.media_session.MediaSessionResponse
import com.musa_kavak.scythe.services.NLService
import java.io.ByteArrayOutputStream

class MediaSessionManager(private val context: Context) {
    private var mediaSessions: List<MediaSessionContent>? = null

    fun updateMediaSessions() {
        val notificationAccess =
            Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
                .contains(context.packageName)
        if (notificationAccess) {
            val sessions = getActiveMediaSessions()
            mediaSessions = sessions
            SocketEmitter(context).AndroidMediaSessionResponse(toResponseList(sessions))
        }
    }

    fun sendActionToMediaSession(packageName: String, actionId: Int) {
        mediaSessions?.forEach { msc ->
            if (msc.packageName == packageName) {
                when (actionId) {
                    0 -> {
                        msc.controls.skipToPrevious()
                        msc.controls.skipToPrevious()
                    }
                    1 -> msc.controls.pause()
                    2 -> msc.controls.play()
                    3 -> msc.controls.stop()
                    4 -> {
                        msc.controls.skipToNext()
                    }
                }
                return
            }
        }
    }

    private fun getActiveMediaSessions(): List<MediaSessionContent> {
        val list = mutableListOf<MediaSessionContent>()
        val manager = context.getSystemService<MediaSessionManager>()
        val component = ComponentName(context, NLService::class.java)
        manager?.let {
            val sessions = it.getActiveSessions(component)
            sessions.forEach { mc ->
                mc.metadata?.let { mtdt ->
                    list.add(
                        MediaSessionContent(
                            mc.packageName,
                            mtdt.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART),
                            mtdt.getString(MediaMetadata.METADATA_KEY_ARTIST),
                            mtdt.getLong(MediaMetadata.METADATA_KEY_DURATION),
                            mtdt.getString(MediaMetadata.METADATA_KEY_ALBUM),
                            mtdt.getString(MediaMetadata.METADATA_KEY_TITLE),
                            mc.transportControls
                        )
                    )
                }
            }
        }
        return list
    }

    private fun toResponseList(list: List<MediaSessionContent>): List<MediaSessionResponse> {
        val newList = mutableListOf<MediaSessionResponse>()
        list.forEach {
            newList.add(
                MediaSessionResponse(
                    it.packageName,
                    bitmapToString(it.albumArt),
                    it.artist,
                    it.duration,
                    it.album,
                    it.title
                )
            )
        }
        return newList
    }

    private fun bitmapToString(bit: Bitmap?): String? {
        bit?.let {
            val stream = ByteArrayOutputStream()

            it.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val imageBytes = stream.toByteArray()
            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }
        return null
    }
}