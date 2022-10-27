package com.musa_kavak.scythe.services

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.musa_kavak.scythe.models.user.User

class UserService {
    companion object {
        private var currentUser: User? = null

        fun getCurrentUser(context: Context): User? {
            return if (currentUser == null) {
                val newUser = getUserFromSharedPreferences(context)
                if (newUser.id == -1) {
                    null
                } else {
                    currentUser = newUser
                    currentUser
                }
            } else {
                currentUser
            }
        }

        private fun getUserFromSharedPreferences(context: Context): User {
            val sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE)

            fun spGetString(key: String): String {
                return sp.getString(key, "")!!
            }
            return User(
                sp.getInt("UserId", -1),
                spGetString("UserFirstName"),
                spGetString("UserLastName"),
                spGetString("UserUserName"),
                spGetString("UserEmail")
            )
        }
    }
}