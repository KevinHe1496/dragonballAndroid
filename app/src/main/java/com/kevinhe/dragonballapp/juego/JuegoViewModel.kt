package com.kevinhe.dragonballapp.juego

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JuegoViewModel: ViewModel() {

    sealed class State {
        data object Loading: State()
        data class Success(val personajes: List<Personaje>): State()
        data class Error(val message: String): State()
    }

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

}