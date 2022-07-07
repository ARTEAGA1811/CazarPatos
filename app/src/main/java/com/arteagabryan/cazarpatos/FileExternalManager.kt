package com.arteagabryan.cazarpatos

import android.app.Activity
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileExternalManager(val actividad: Activity) : FileHandler {

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    //Verifica que el archivo exista en el directorio de almacenamiento externo
    fun validarArchivo(): Unit{
        val archivo = File(
            actividad.getExternalFilesDir(null),
            SHAREDINFO_FILENAME
        )

        if (!archivo.exists()) {
            archivo.createNewFile()
        }
    }

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        validarArchivo()

        if (isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedWriter().use { outputStream ->
                outputStream.write(datosAGrabar.first)
                outputStream.write(System.lineSeparator())
                outputStream.write(datosAGrabar.second)

            }
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        validarArchivo()

        var email: String = ""
        var password: String = ""

        if (isExternalStorageReadable()) {
            FileInputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    SHAREDINFO_FILENAME
                )
            ).bufferedReader().use {
                val datoLeido = it.readText()
                try{
                    val textArray = datoLeido.split(System.lineSeparator())
                    email = textArray[0]
                    password = textArray[1]
                } catch (e: Exception){
                    Log.d("TAG", "Error al obtener datos del archivo")
                }


            }
        }
        return (email to password)

    }

}