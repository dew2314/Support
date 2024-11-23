package com.eseul.support.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eseul.support.repository.JoinRepository

class JoinViewModelFactory(private val joinRepository: JoinRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JoinViewModel::class.java)) {
            return JoinViewModel(joinRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
