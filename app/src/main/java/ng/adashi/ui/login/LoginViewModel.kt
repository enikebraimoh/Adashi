package ng.adashi.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.network.SessionManager
import ng.adashi.repository.AuthRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(val authRepository: AuthRepository) : ViewModel() {

    @Inject
    lateinit var sessions: SessionManager

    var email: String? = null
    var password: String? = null

    private val _navigateToDashboard = MutableLiveData<Boolean>()
    val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard

    private val _login = MutableLiveData<DataState<LoginToken>>()
    val login: LiveData<DataState<LoginToken>> get() = _login

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
        authRepository.LogUserNewIn(login).onEach { state ->
            _login.value = state
            when (state) {
                is DataState.Success<LoginToken> -> {
                    sessions.saveAuthToken(state.data.accessToken)
                    sessions.saveCurrentAgent(state.data.user)
                    App.token = state.data.accessToken
                    navigate()
                }
            }
        }.launchIn(viewModelScope)
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

    fun navigate() {
        _navigateToDashboard.value = true
    }

    fun navigationDone() {
        _navigateToDashboard.value = false
    }

}