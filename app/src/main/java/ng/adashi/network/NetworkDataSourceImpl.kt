package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginResponse

class NetworkDataSourceImpl () : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails): LoginResponse {
           val response = RetrofitInstance.Api.Login(loginDetails)
           return response
    }

}