package com.example.gg.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.gg.R


class Activity_welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun register(view: View)
    {
        startActivity(Intent(this,Activity_register::class.java))
    }

    fun login(view: View)
    {
        startActivity(Intent(this,Activity_login::class.java))
    }
}