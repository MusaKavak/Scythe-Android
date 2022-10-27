package com.musa_kavak.scythe.retrofit


import com.musa_kavak.scythe.models.user.UserLoginBody
import com.musa_kavak.scythe.models.user.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IDataAccess {

   @POST("users/userNameLogin")
    fun loginWithUserName(@Body userLoginBody: UserLoginBody):Call<UserLoginResponse>
}