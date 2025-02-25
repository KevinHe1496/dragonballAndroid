package com.kevinhe.dragonballapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

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
}