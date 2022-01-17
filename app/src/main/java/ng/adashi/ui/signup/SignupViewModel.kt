package ng.adashi.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.repository.AuthRepository
import ng.adashi.ui.signup.models.SignUpData
import ng.adashi.ui.signup.models.SignUpResponse
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import javax.inject.Inject

@HiltViewModel
class SignupViewModel
@Inject
constructor(val authRepository: AuthRepository) : ViewModel() {


    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null

    private val _signUp = MutableLiveData<DataState<SignUpResponse>>()
    val signUp: LiveData<DataState<SignUpResponse>> get() = _signUp

    private val _firstNameError = MutableLiveData<String>()
    val firstNameError: LiveData<String> get() = _firstNameError

    private val _lastNameError = MutableLiveData<String>()
    val lastNameError: LiveData<String> get() = _lastNameError

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> get() = _passwordError


    fun signUp() {
        if (validateFirstName()) {
            if (validateLastName()  ) {
                if (verifyEmail()){
                    if (verifyPassword()){
                        signUp(SignUpData(email!!,firstName!!,lastName!!, password!!))
                    }
                }
            }
        }
    }

    //call the login function from the repository
    fun signUp(login: SignUpData) {
        authRepository.signUp (login).onEach { state ->
            _signUp.value = state
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

    private fun validateFirstName(): Boolean {
        return if (firstName == null || firstName!! == "") {
            _firstNameError.value = "this field cannot be left blank"
            false
        } else if (firstName!!.contains("[0-9]".toRegex())) {
            _firstNameError.value = "name cannot contain numbers"
            false
        } else if ((firstName!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _firstNameError.value = "name cannot contain special characters"
            false
        } else if (firstName!!.contains("\\s+".toRegex())) {
            _firstNameError.value = "name cannot contain white space"
            false
        } else {
            _firstNameError.value = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        return if (lastName == null || lastName == "") {
            _lastNameError.value = "this field cannot be left blank"
            false
        } else if (lastName!!.contains("[0-9]".toRegex())) {
            _lastNameError.value = "name cannot contain numbers"
            false
        } else if ((lastName!!.contains("[^A-Za-z0-9 ]".toRegex()))) {
            _lastNameError.value = "name cannot contain special characters"
            false
        } else if (lastName!!.contains("\\s+".toRegex())) {
            _lastNameError.value = "name cannot contain white space"
            false
        } else {
            _lastNameError.value = null
            true
        }
    }



}