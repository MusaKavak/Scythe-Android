package com.musa_kavak.scythe.models.file

data class RemoteFile(
    val path: String,
    val fileName : String,
    val isDirectory : Boolean,
    val fileExtension : String? = null,
    val subDirectories : List<RemoteFile>? = null
)
