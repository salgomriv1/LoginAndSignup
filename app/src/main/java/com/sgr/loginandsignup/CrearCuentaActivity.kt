package com.sgr.loginandsignup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.sgr.loginandsignup.databinding.ActivityCrearCuentaBinding

class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityCrearCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        binding.btnCrear.setOnClickListener() {

            var name = binding.etNombre.text.toString()
            var email = binding.etCrearCuentaEmail.text.toString()
            var pass1 = binding.etCrearCuentaPass.text.toString()
            var pass2 = binding.etCrearCuentaConfirmPass.text.toString()

            //Habria que comprobar que los campos no estan vacios y que la contraseña tiene 6 caracteres
            //Se comprueba que las dos contraseñas coinciden

            if (pass1.equals(pass2)) {
                createAccount (name, email, pass1)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                    .show()
                binding.etCrearCuentaPass.requestFocus()
            }
        }

    }

    //Metodo que crea la cuenta
    private fun createAccount(name: String, email: String, pass: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //Recuperamos el usuario
                    val user = firebaseAuth.currentUser
                    //Preparamos las modificaciones del profile
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    //Modificamos el profile
                    user!!.updateProfile(profileUpdates)

                    Toast.makeText(this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {

                    //Se indica si el fallo ha sido que el correo esta en uso
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(this, "Este correo esta en uso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this, "Algo salio mal, Error: " + exception,Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }

}