package com.kevinhe.dragonballapp.juego

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinhe.dragonballapp.model.Personaje
import com.kevinhe.dragonballapp.repository.PersonajesRepository
import com.kevinhe.dragonballapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JuegoViewModel: ViewModel() {

    sealed class State {
        data object Loading : State()
        data class Success(val personajes: List<Personaje>) : State()
        data class Error(val message: String) : State()
        //cuando se ha seleccionado un personaje
        data class PersonajeSeleccionado(val personaje: Personaje) : State()
    }

    private val _uistate = MutableStateFlow<State>(State.Loading)
    private val personajeRepository = PersonajesRepository()

    @VisibleForTesting
    val userRepository = UserRepository()

    val uiState: StateFlow<State> = _uistate.asStateFlow()

    fun golpearPersonaje(personaje: Personaje) {
        personaje.vidaActual -= 10
    }

    // este personaje se a seleccionado
    fun personajeSeleccionado(personaje: Personaje) {
        personaje.vecesSelecionado++
        _uistate.value = State.PersonajeSeleccionado(personaje)
    }


    fun personajeDeseleccionado() {
        val resultado = personajeRepository.fetchPersonajes(
            userRepository.getToken()
        )
        when (resultado) {
            is PersonajesRepository.PersonajesResponse.Success -> {
                _uistate.value = State.Success(resultado.personajes)
            }

            is PersonajesRepository.PersonajesResponse.Error -> {
                _uistate.value = State.Error(resultado.message)
            }
        }
    }


    fun descargarPersonajes(sharedPreferences: SharedPreferences) {

        viewModelScope.launch(Dispatchers.IO) {
            _uistate.value = State.Loading

            val resultado = personajeRepository.fetchPersonajes(userRepository.getToken(), sharedPreferences)
            when (resultado) {
                is PersonajesRepository.PersonajesResponse.Success -> {
                    _uistate.value = State.Success(resultado.personajes)
                }

                is PersonajesRepository.PersonajesResponse.Error -> {
                    _uistate.value = State.Error(resultado.message)
                }

            }
        }

    }
}