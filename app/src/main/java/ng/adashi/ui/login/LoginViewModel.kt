package ng.adashi.ui.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ng.adashi.models.login.LoginDetails
import ng.adashi.models.login.LoginResponse
import ng.adashi.models.login.LoginToken
import ng.adashi.repository.LoginRepository
import ng.adashi.utils.DataState
import ng.adashi.utils.Resource
import ng.adashi.utils.convertErrorBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class LoginViewModel(val app : Application, val loginRepository: LoginRepository) : ViewModel() {

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
                logUsersIn(LoginDetails(email!!,password!!))
            }
        }
    }

    //call the function that gets all vendors from the repository
    fun logUsersIn(login : LoginDetails) = viewModelScope.launch {
        loginFromRepository(login)
    }

    private suspend fun loginFromRepository(logindetails : LoginDetails) {

        try {
            if (hasInternet()) {
                _login.value = DataState.Loading
                try {
                    val response = loginRepository.logMeIn(logindetails)
                    if (response.isSuccessful) {
                        response.body().let { response ->
                            _login.value =   DataState.Success(response!!)
                        }
                    }
                }catch (e: Throwable){
                    when (e) {
                        is IOException -> _login.value = (DataState.Error(e))
                        is HttpException -> {
                            val status = e.code()
                            val res = convertErrorBody(e)
                            _login.value = (DataState.GenericError(status, res))
                        }
                    }
                }
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _login.value = (DataState.Error(t))
            }

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
        } else {
            _passwordError.value = null
            true
        }
    }

    private fun hasInternet(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true

                    else -> false
                }
            }
        }

        return false
    }

}