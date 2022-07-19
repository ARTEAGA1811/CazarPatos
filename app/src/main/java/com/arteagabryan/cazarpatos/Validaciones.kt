package com.arteagabryan.cazarpatos

import java.util.regex.Pattern

class Validaciones {

        //Pat´ron de validacion del correo
        private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )



        public fun validarCorreo(str: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
        }

        public fun validarContrasenia(contra: String): Boolean{
            return contra.length >= 8
        }

}