package com.kevinhe.dragonballapp.model

class Personaje(val id: String, val nombre: String, val imagenUrl: String, var vidaActual: Int, val vidaTotal: Int = 100, var vecesSelecionado: Int = 0) {
}