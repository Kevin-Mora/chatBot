package com.example.gg.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gg.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
    fun load_activity_register(v: View){
       // setContentView(R.layout.activity_register)
       startActivity(Intent(this,Activity_register::class.java))
    }
    fun load_activity_login(v: View){
        //setContentView(R.layout.activity_login)
        startActivity(Intent(this,Activity_login::class.java))
    }
}

