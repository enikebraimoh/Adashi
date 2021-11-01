package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse

class NetworkDataSourceImpl () : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails): LoginResponse {
           val response = RetrofitInstance.api.Login(loginDetails)
           return response
    }

    override suspend fun GetWallet(wallet_id: String): AgentWalletResponse {
        val response = RetrofitInstance.api.GetAgentWallet(wallet_id)
        return response
    }

}