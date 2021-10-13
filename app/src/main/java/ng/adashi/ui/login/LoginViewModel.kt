package ng.adashi.ui.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ng.adashi.models.login.LoginDetails
import ng.adashi.models.login.LoginResponse
import ng.adashi.models.login.LoginToken
import ng.adashi.repository.LoginRepository
import ng.adashi.utils.Resource
import retrofit2.Response
import java.io.IOException

class LoginViewModel(val app : Application, val loginRepository: LoginRepository) : ViewModel() {

    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login


    //call the function that gets all vendors from the repository
    fun logUsersIn(login : LoginDetails) = viewModelScope.launch {
        loginFromRepository(login)
    }

    private suspend fun loginFromRepository(logindetails : LoginDetails) {
        _login.value = Resource.loading()

        try {
            if (hasInternet()) {
                val response = loginRepository.logMeIn(logindetails)
                _login.value = handleLoginResponse(response)
            }
            else _login.value = Resource.error("No Internet Connection")

        } catch (t: Throwable) {
            when (t) {
                is IOException -> _login.value = (Resource.error("Network Failure"))
                else -> _login.value = (Resource.error("Internal Error"))
            }

        }

    }

    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body().let { loginResponse ->

                return Resource.success(loginResponse)
            }
        }
        return Resource.error(response.message())
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