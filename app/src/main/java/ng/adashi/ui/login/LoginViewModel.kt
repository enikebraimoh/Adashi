package ng.adashi.ui.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.DataState
import ng.adashi.utils.Utils

class LoginViewModel(val app: Application, val authRepository: AuthRepository) : ViewModel() {

    var email: String? = null
    var password: String? = null

    private val _login = MutableLiveData<DataState<LoginResponse>>()
    val login: LiveData<DataState<LoginResponse>> get() = _login

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError

    fun login() {
        if (verifyEmail()) {
            if (verifyPassword()) {
                logUsersIn(LoginDetails(email!!, password!!))
            }
        }
    }

    //call the login function from the repository
    fun logUsersIn(login: LoginDetails) {
        viewModelScope.launch {
            authRepository.LogUserNewIn(login).onEach { state ->
                _login.value = state
            }.launchIn(viewModelScope)
        }
    }

    private fun verifyEmail(): Boolean {
        return if (email == null || email == "") {
            _emailError.value = "field must not be blank"
            false
        } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = null
            true
        } else {
            _emailError.value = "invalid email"
            false
        }
    }

    private fun verifyPassword(): Boolean {
        return if (password == null || password == "") {
            _passwordError.value = "Password field cannot be blank"
            false
        } else if (password!!.length < 6) {
            _passwordError.value = "Password field must have at least 6 characters"
            false
        } else {
            _passwordError.value = null
            true
        }
    }

}