package com.sgr.loginandsignup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sgr.loginandsignup.databinding.ActivityRecordarPassBinding

class RecordarPassActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRecordarPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecordarPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        firebaseAuth = Firebase.auth

        binding.btnReestablecer.setOnClickListener() {

            val email = binding.etEmailReest.text.toString()
            restablecerContraseña(email)
        }
    }

    //Metodo que envia mail de reestablecimiento de contraseña
    private fun restablecerContraseña(email: String) {

        if (!email.isEmpty()) {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Se envió correo de reseteo de pass", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    } else {
                        Toast.makeText(this, "No se encontró la cuenta", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(this, "El email no puede estar vacio", Toast.LENGTH_SHORT).show()
        }
    }
}