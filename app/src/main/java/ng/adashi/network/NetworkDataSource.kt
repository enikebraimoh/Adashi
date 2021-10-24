package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginResponse

}