package com.arteagabryan.cazarpatos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NuevoUsuarioActivity : AppCompatActivity() {

    private lateinit var emailUsuario: EditText
    private lateinit var passwordUsuario: EditText
    private lateinit var btnAgregar: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_usuario)

        emailUsuario = findViewById(R.id.editTextEmail)
        passwordUsuario = findViewById(R.id.editTextPassword)
        btnAgregar = findViewById(R.id.buttonAgregar)
        // Initialize Firebase Auth
        auth = Firebase.auth


        btnAgregar.setOnClickListener {
            val email = emailUsuario.text.toString()
            val clave = passwordUsuario.text.toString()

            if (!ValidarDatosRequeridos())
                return@setOnClickListener

            SignUpNewUser(email, clave)

        }

    }


    fun SignUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(EXTRA_LOGIN, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "New user saved.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(EXTRA_LOGIN, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }


    private fun ValidarDatosRequeridos(): Boolean {
        val misValidaciones = Validaciones();
        val email = emailUsuario.text.toString()
        val clave = passwordUsuario.text.toString()
        if (email.isEmpty()) {
            emailUsuario.setError("El email es obligatorio")
            emailUsuario.requestFocus()
            return false
        }
        if (!misValidaciones.validarCorreo(email)) {
            emailUsuario.setError("El email no es válido")
            passwordUsuario.requestFocus()
            return false
        }
        if (clave.isEmpty()) {
            emailUsuario.setError("La clave es obligatoria")
            passwordUsuario.requestFocus()
            return false
        }
        if (!misValidaciones.validarContrasenia(clave)) {
            emailUsuario.setError("La clave debe tener al menos 8 caracteres")
            passwordUsuario.requestFocus()
            return false
        }
        return true
    }
}