package com.loe.fanmark.ui.auth.login

import android.app.Activity
import android.app.Application
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.loe.fanmark.R
import com.loe.fanmark.network.AuthTransferObjects
import com.loe.fanmark.network.AuthTransferObjects.LoginBodyObject
import com.loe.fanmark.network.AuthTransferObjects.SignUpObject
import com.loe.fanmark.network.ResultWrapper.*
import com.loe.fanmark.network.Service
import com.loe.fanmark.repository.AuthRepository
import com.loe.fanmark.util.Tools
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import java.lang.IllegalArgumentException

enum class AuthAction {
    LOGIN,
    SIGN_UP
}

enum class AuthApiStatus {
    LOADING,
    ERROR,
    DONE,
    OVER
}


class AuthViewModel(application: Application): AndroidViewModel(application) {

    private val myApplication = application

    private val _status = MutableLiveData<AuthApiStatus>()
    val status: LiveData<AuthApiStatus>
        get() = _status
    private val _isUsernameActive = MutableLiveData<Boolean?>(null)
    val isUsernameActive: LiveData<Boolean?>
        get() = _isUsernameActive

    private val _showToast = MutableLiveData<List<String>>()
    val showToast: LiveData<List<String>>
        get() = _showToast

    private val _navigateSignUp = MutableLiveData<Boolean>(false)
    val navigateSignUp: LiveData<Boolean>
        get() = _navigateSignUp


    private val authRepository = AuthRepository(Service)


    fun <T> submitAuthData(action: AuthAction, body: T) = viewModelScope.launch {

        _status.value = AuthApiStatus.LOADING

        when(action) {
            AuthAction.LOGIN -> {
                when (val login = authRepository.login(Dispatchers.IO, body as LoginBodyObject)) {
                    is Success -> {
                        _status.value = AuthApiStatus.DONE
                        Tools.l(login.value.message)
                    }
                    is GenericError -> {
                        _status.value = AuthApiStatus.ERROR
                        Tools.l(login.error?.message!!)
                    }
                    NetworkError -> {
                        _status.value = AuthApiStatus.ERROR
                        Tools.l("NetworkError")
                    }
                }
            }
            AuthAction.SIGN_UP -> {

                when (val signUp = authRepository.signUp(Dispatchers.IO, body as SignUpObject)) {
                    is Success -> {
                        _status.value = AuthApiStatus.DONE
                        Tools.l(signUp.value.message)
                    }
                    is GenericError -> {
                        _status.value = AuthApiStatus.ERROR
                        Tools.l(signUp.error?.message!!)
                    }
                    NetworkError -> {
                        _status.value = AuthApiStatus.ERROR
                        Tools.l("NetworkError")
                    }
                }

            }
        }


    }

    fun completeAuth() {
        _status.value = AuthApiStatus.OVER
    }

    fun changeStatus() {
        if (_isUsernameActive.value == null) {
            _isUsernameActive.value = false
        }
        else
            _isUsernameActive.value = !_isUsernameActive.value!!
    }

    fun displayToast(message: String, style: String) {
        _showToast.value = listOf(message, style)
    }

    fun displayToastComplete() {
        _showToast.value = null
    }

    fun navigateSignUpFragment() {
        _navigateSignUp.value = true
    }

    fun navigateSignUpFragmentComplete() {
        _navigateSignUp.value = false
    }

    class Factory(private val application: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java))
                return AuthViewModel(application) as T
            throw IllegalArgumentException("Unable to construct view model")
        }

    }

}