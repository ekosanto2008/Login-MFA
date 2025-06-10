package com.santoso.loginmfa

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)

    val loginResult = MutableLiveData<Boolean>()
    val registerResult = MutableLiveData<Boolean>()
    private val userExists = MutableLiveData<Boolean>()

    fun checkUserExists(username: String) {
        viewModelScope.launch {
            val user = repository.getUser(username)
            userExists.postValue(user != null)
        }
    }

    fun register(username: String, password: String, mfaSecret: String) {
        viewModelScope.launch {
            repository.registerUser(User(username = username, password = password, mfaSecret = mfaSecret))
            registerResult.postValue(true)
        }
    }

    fun login(username: String, password: String, otp: String) {
        viewModelScope.launch {
            val user = repository.getUser(username)
            if (user != null && user.password == password && GoogleMFAHelper.verifyCode(user.mfaSecret, otp)) {
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        }
    }
}