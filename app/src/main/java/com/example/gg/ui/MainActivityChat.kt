package com.example.gg.ui

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gg.R
import com.example.gg.data.Message
import com.example.gg.utils.BotResponse
import com.example.gg.utils.Constants.OPEN_GOOGLE
import com.example.gg.utils.Constants.OPEN_SEARCH
import com.example.gg.utils.Constants.RECEIVE_ID
import com.example.gg.utils.Constants.SEND_ID
import com.example.gg.utils.Time
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
//Hay errores con este import
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivityChat : AppCompatActivity() {

    //Me marcaba como error unresolved reference, así que use el metodo que usan las otras activitys para
    //usar las view
    private lateinit var  adapter: MessagingAdapter
    //private lateinit var rv_messages: RecyclerView
    // Nombres a los que el bot se identifica
    private var botList = listOf("Ed", "Profesor Ed", "Super Ed", "Mr. Ed")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customMessage("Hola!!! hoy estas hablando con ${botList[random]} como te puedo ayudar?")

        //Logout
        //En esta parte del código obtenemos el email que enviamos desde
        //la activity del login
        val bundle:Bundle? = intent.extras
        val email:String?  = bundle?.getString("email")
        setup(email)
    }

    private fun clickEvents() {
        //Me marcaba como error unresolved reference, así que use el metodo que usan las otras activitys para
        //usar views
        btn_send.setOnClickListener{
            sendMessage()
        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }

            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }


    private fun botResponse(message: String) {

        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main){

                val response = BotResponse.basicResponse(message)

                adapter.insertMessage(Message(response,RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent(ACTION_VIEW))
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }

                    OPEN_SEARCH -> {
                        val site = Intent(Intent(ACTION_VIEW))
                        val searchTerm: String? = message.substringAfter("busca")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)

                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }


    private fun setup(email: String?){
        //Se agrega el ? para evitar excepciones de puntero nulo
        textViewUserName?.text = email

        //Boton del logout
    }

    //Funcion que gestiona el logout
    public fun LogOut(view: View){
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }
}