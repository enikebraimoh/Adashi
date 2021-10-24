package ng.adashi.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ng.adashi.repository.AuthRepository

class LoginFactory (
    val app : Application,
    val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(app, authRepository) as T
    }
}