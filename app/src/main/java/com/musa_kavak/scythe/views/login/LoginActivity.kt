package com.musa_kavak.scythe.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.musa_kavak.scythe.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var bd : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd  = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bd.root);
    }
}