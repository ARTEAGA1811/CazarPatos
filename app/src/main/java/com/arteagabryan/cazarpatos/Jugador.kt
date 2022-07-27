package com.arteagabryan.cazarpatos

data class Jugador(var usuario: String, var patosCazados: Int) {
    constructor() : this("", 0)
}