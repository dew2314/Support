package com.eseul.support.repository

import android.util.Log
import com.eseul.support.model.UserModel
import com.eseul.support.model.api.AuthApi

class AuthRepository(private val authApi: AuthApi) {

    suspend fun login(username: String, password: String): UserModel? {
        val body = hashMapOf(
            "loginId" to username,  // username
            "password" to password  // password
        )

        return try {
            authApi.login(body).body()
        } catch (exception : Exception) {
            Log.d("TAG", "login: ${exception.message}")
            null
        }
    }
}
