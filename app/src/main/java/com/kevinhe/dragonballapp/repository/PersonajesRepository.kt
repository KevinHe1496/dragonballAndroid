package com.kevinhe.dragonballapp.repository

import com.google.gson.Gson
import com.kevinhe.dragonballapp.juego.JuegoViewModel.State
import com.kevinhe.dragonballapp.model.Personaje
import com.kevinhe.dragonballapp.model.PersonajeDto
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class PersonajesRepository {

    private val BASE_URL = "https://dragonball.keepcoding.education/api/"
    private var listaPersonajes = listOf<Personaje>()

    sealed class PersonajesResponse {
        data class Success(val personajes: List<Personaje>) : PersonajesResponse()
        data class Error(val message: String) : PersonajesResponse()
    }

    fun fetchPersonajes(token: String) : PersonajesResponse {
        if (listaPersonajes.isNotEmpty()) return PersonajesResponse.Success(listaPersonajes)
        var client = OkHttpClient()
        val url = "${BASE_URL}heros/all"

        val formBody = FormBody.Builder()
            .add("name", "")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Authorization", "Bearer $token")
            .build()

        val call = client.newCall(request)
        val response = call.execute()

       return if (response.isSuccessful) {
            val personajesDto: Array<PersonajeDto> =
                Gson().fromJson(response.body?.string(), Array<PersonajeDto>::class.java)

           listaPersonajes = personajesDto.map {
               Personaje(
                   id = it.id,
                   nombre = it.name,
                   imagenUrl = it.photo,
                   vidaActual = 100,
                   vidaTotal = 100,
               )
           }
            PersonajesResponse.Success(listaPersonajes)
        } else {
            PersonajesResponse.Error("Error al descargar los personajes. ${response.message}")
        }
    }
}