package com.eseul.support.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eseul.support.model.UserModel
import com.eseul.support.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<UserModel>()
    val loginResult: LiveData<UserModel> get() = _loginResult

    suspend fun login(username: String, password: String) {
        _loginResult.value=withContext(Dispatchers.IO) {
            authRepository.login(username, password)
        }
    }
}
