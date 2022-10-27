package com.musa_kavak.scythe.models.user

data class UserLoginBody(
    val emailOrUserName:String,
    val password:String
)