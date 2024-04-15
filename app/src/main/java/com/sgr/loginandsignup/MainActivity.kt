package com.sgr.loginandsignup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    //Se prepraran las variables
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        val etEmail: TextView = findViewById(R.id.etEmail)
        val etPass: TextView = findViewById(R.id.etPass)
        val btnRegistrar: TextView = findViewById(R.id.btnCrearCuenta)
        val btnOlvidar: TextView = findViewById(R.id.tvOlvido)

        //Se incializa la variable
        firebaseAuth = Firebase.auth

        btnSignIn.setOnClickListener() {
            //Habria que comprobar que los campos no estan vacios
            signIn(etEmail.text.toString(),etPass.text.toString())
        }

        btnRegistrar.setOnClickListener() {
            val intent = Intent (this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        btnOlvidar.setOnClickListener() {
            val intent = Intent (this, RecordarPassActivity::class.java)
            startActivity(intent)
        }

    }

    //Metodo para realizar la autenticacion
    private fun signIn(email : String, pass: String) {

        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {

                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext, "Autenticacion exitosa", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(baseContext,"Error de email o contraseña", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

}