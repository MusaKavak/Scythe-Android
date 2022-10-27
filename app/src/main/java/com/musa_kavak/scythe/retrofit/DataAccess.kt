package com.musa_kavak.scythe.retrofit

import com.musa_kavak.scythe.models.user.UserLoginBody
import com.musa_kavak.scythe.models.user.UserLoginResponse
import retrofit2.Call

class DataAccess {
    private val api = ApiClient.getClient().create(IDataAccess::class.java)


    fun loginWithUserName (userLoginBody: UserLoginBody): Call<UserLoginResponse>{
        return api.loginWithUserName(userLoginBody)
    }
}