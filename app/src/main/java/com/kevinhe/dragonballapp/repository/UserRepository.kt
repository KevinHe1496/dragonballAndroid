package com.kevinhe.dragonballapp.repository

import androidx.annotation.VisibleForTesting
import kotlin.random.Random

class UserRepository {

    companion object {
        private var token = ""
    }

    sealed class LoginResponse {
        data object Success : LoginResponse()
        data class Error(val message: String) : LoginResponse()
    }

    fun login(usuario: String, password: String): LoginResponse {
      return if (Random.nextBoolean()) {
            token = "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJrZXZpbl9oZXJlZGlhMTBAaG90bWFpbC5jb20iLCJpZGVudGlmeSI6IkY2QTMyREQ5LTEwREYtNDEzMi1BQTI2LUFENTZEMURGN0U2RSJ9.DjTM9QRu5zFtFftxeti07olNxtBLCJqikI1RE7XlGIU"
            LoginResponse.Success
        } else {
            LoginResponse.Error("501")
        }
    }

    fun getToken() : String = token
    @VisibleForTesting
    fun setToken(token: String) { UserRepository.token = token}
}