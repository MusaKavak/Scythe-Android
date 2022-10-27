package com.musa_kavak.scythe.models.user

data class UserLoginResponse(
    val user: UserResponse,
    val status: String
)
