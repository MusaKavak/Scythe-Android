package com.musa_kavak.scythe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.musa_kavak.scythe.services.UserService
import com.musa_kavak.scythe.views.home.HomeActivity
import com.musa_kavak.scythe.views.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlUserState()
    }

    private fun controlUserState() {
        val user = UserService.getCurrentUser(this)

        val intent = if (user == null) {
            Intent(this, LoginActivity::class.java)
        }else{
            Intent(this,HomeActivity::class.java)
        }

        startActivity(intent)
    }

}