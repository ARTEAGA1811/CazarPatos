package com.arteagabryan.cazarpatos

import android.app.Activity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedSharedPreferencesManager(val actividad: Activity): FileHandler {
    val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {

        val sharedPref = EncryptedSharedPreferences.create(
            "secret_shared_preferences",
            masterKeyAlias,
            actividad,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val editor = sharedPref.edit()
        // Guarda los datos a grabar, se pone como clave el Login_Key y el Password_key
        editor.putString(LOGIN_KEY , datosAGrabar.first)
        editor.putString(PASSWORD_KEY, datosAGrabar.second)
        editor.apply()
    }

    override fun ReadInformation(): Pair<String, String> {
        val sharedPref = EncryptedSharedPreferences.create(
            "secret_shared_preferences",
            masterKeyAlias,
            actividad,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Aqui lee los datos guardados, en caso que no haya datos el por defecto es el vacio "".
        val email = sharedPref.getString(LOGIN_KEY,"").toString()
        val clave = sharedPref.getString(PASSWORD_KEY,"").toString()
        return (email to clave)
    }

}