package com.kevinhe.dragonballapp.juego

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinhe.dragonballapp.model.Personaje
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JuegoViewModel: ViewModel() {

    sealed class State {
        data object Loading: State()
        data class Success(val personajes: List<Personaje>): State()
        data class Error(val message: String): State()
    }

    private val _uistate = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uistate.asStateFlow()

    fun descargarPersonajes() {

        viewModelScope.launch {
        _uistate.value = State.Loading
        delay(2000L)
        _uistate.value = State.Success(fetchPersonajes())
        }
    }

    private fun fetchPersonajes() = listOf(
        Personaje("1", "Goku", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300", 100, 100),
        Personaje("2", "Vegeta", "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300", 100, 100),
        Personaje("3", "C-17", "https://cdn.alfabetajuega.com/alfabetajuega/2019/10/dragon-ball-androide-17.jpg?width=300", 100, 100),
        Personaje("4", "C-18", "https://cdn.alfabetajuega.com/alfabetajuega/2020/01/Androide-18.jpg?width=300", 100, 100)

    )
}