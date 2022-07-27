package com.arteagabryan.cazarpatos

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    lateinit var manejadorArchivo: FileHandler
    lateinit var editTextEmail:EditText
    lateinit var editTextPassword: EditText
    lateinit var buttonLogin:Button
    lateinit var buttonNewUser: Button
    lateinit var checkBoxRecordarme: CheckBox
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Inicialización de variables
        //manejadorArchivo = SharedPreferencesManager(this) // Para la parte 1
        //manejadorArchivo = EncryptedSharedPreferencesManager(this) // Para la parte 3
        manejadorArchivo = FileExternalManager(this) // Para la parte 4
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNewUser = findViewById(R.id.buttonNewUser)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme)

        LeerDatosDePreferencias()

        //Eventos clic
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val clave = editTextPassword.text.toString()

            if (!ValidarDatosRequeridos())
                return@setOnClickListener

            //Guardar datos en preferencias.
            GuardarDatosEnPreferencias()

            //Si pasa validación de datos requeridos, ir a pantalla principal
            val intencion = Intent(this, MainActivity::class.java)
            intencion.putExtra(EXTRA_LOGIN, cortarSoloNombre(email))
            startActivity(intencion)

        }
        buttonNewUser.setOnClickListener{

        }
        mediaPlayer=MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.isLooping = true
        mediaPlayer.start()




    }

    private fun ValidarDatosRequeridos():Boolean{
        val misValidaciones = Validaciones();
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        if (email.isEmpty()) {
            editTextEmail.setError("El email es obligatorio")
            editTextEmail.requestFocus()
            return false
        }
        if(!misValidaciones.validarCorreo(email)){
            editTextEmail.setError("El email no es válido")
            editTextEmail.requestFocus()
            return false
        }
        if (clave.isEmpty()) {
            editTextPassword.setError("La clave es obligatoria")
            editTextPassword.requestFocus()
            return false
        }
        if (!misValidaciones.validarContrasenia(clave)) {
            editTextPassword.setError("La clave debe tener al menos 8 caracteres")
            editTextPassword.requestFocus()
            return false
        }
        return true
    }

    private fun LeerDatosDePreferencias(){
        //Me trae un Pair con el email y contraseña almacenados.
        val listadoLeido = manejadorArchivo.ReadInformation()

        // Si encuentra datos almacenados, hace que aparezca seleccionada la casilla del checkbox
        if(listadoLeido.first != null){
            checkBoxRecordarme.isChecked = true
        }

        // Asigna los valores a las casillas de input.
        editTextEmail.setText ( listadoLeido.first )
        editTextPassword.setText ( listadoLeido.second )
    }


    // Si hay el checkbox guarda los datos en preferencias o sino guarda vacio.
    private fun GuardarDatosEnPreferencias(){
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        val listadoAGrabar:Pair<String,String>
        if(checkBoxRecordarme.isChecked){
            listadoAGrabar = email to clave
        }
        else{
            listadoAGrabar ="" to ""
        }
        manejadorArchivo.SaveInformation(listadoAGrabar)
    }


    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }


    fun cortarSoloNombre(correo: String): String{
        val posArroba = correo.indexOf('@')

        //Retorna el substring hasta antes del arroba
        if (posArroba!= -1){
            return correo.substring(0, posArroba)
        }

        //En caso que no detecte el arroba me devuelve el correo completo
        return correo

    }


}