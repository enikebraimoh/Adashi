package ng.adashi.repository

import ng.adashi.models.login.LoginDetails
import ng.adashi.network.RetrofitInstance

class LoginRepository {

    suspend fun logMeIn(login : LoginDetails) = RetrofitInstance.Api.Login(login)
}