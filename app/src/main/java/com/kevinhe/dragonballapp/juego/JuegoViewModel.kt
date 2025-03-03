package com.kevinhe.dragonballapp.juego

import android.media.session.MediaSession.Token
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinhe.dragonballapp.model.Personaje
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class JuegoViewModel: ViewModel() {

    private val BASE_URL = "https://dragonball.keepcoding.education/api/"
    private var token: String? = null

    sealed class State {
        data object Loading: State()
        data class Success(val personajes: List<Personaje>): State()
        data class Error(val message: String): State()
        data class PersonajeSeleccionado(val personaje: Personaje): State()
    }

    private val _uistate = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uistate.asStateFlow()

    fun actualizarToken(token: String) {
        this.token = token
    }

    fun personajeSeleccionado(personaje: Personaje) {
        _uistate.value = State.PersonajeSeleccionado(personaje)
    }

    fun descargarPersonajes() {

        viewModelScope.launch {
        _uistate.value = State.Loading

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

            if (response.isSuccessful) {
                //TODO Analizar la respuesta que viene en json y pasarlo a lista
                _uistate.value = State.Success(listOf())
            } else {
                _uistate.value = State.Error("Error al descargar los personajes. ${response.message}")
            }
        }
    }

}