package com.kevinhe.dragonballapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinhe.dragonballapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Idle)
    val uiState: StateFlow<State> = _uiState

    private val userRepository = UserRepository()

    sealed class State {
        data object Idle: State()
        data object Loading: State()
        data object Success: State()
        data class Error(val message: String, val errorCode: Int) : State()
    }

    fun iniciarLogin(usuario: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            delay(2000L)
            val loginResponse = userRepository.login(usuario, password)
            when(loginResponse) {
                is UserRepository.LoginResponse.Success -> {
                    _uiState.value = State.Success
                }
                is UserRepository.LoginResponse.Error -> {
                    _uiState.value = State.Error("Error con la contraseña o la conexión a internet", 401)
                }
            }
        }
    }


    fun guardarUsuaruo(preferences: SharedPreferences, usuario: String , password: String) {
        // esto ejecuta en segundo hilo
        viewModelScope.launch(Dispatchers.IO) {
        preferences?.edit()?.apply {
            putString("Usuario", usuario)
            putString("Password", password)
            apply()
            }
        }
    }

    // TODO si el usarui ya ha hecho login, entonces no volver a pedirselo
    // si el usuario le ha dado a recordar usuario y contraseña. Puedes mostrarlo
}