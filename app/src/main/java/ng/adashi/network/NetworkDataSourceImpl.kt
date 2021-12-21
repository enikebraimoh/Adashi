package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse
import javax.inject.Inject

class NetworkDataSourceImpl
@Inject
constructor (private val retrofitApis : AdashiApis) : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails):  LoginResponse {
           return retrofitApis.Login(loginDetails)
    }

    override suspend fun GetWallet(wallet_id: String): AgentWalletResponse {
     return retrofitApis.GetAgentWallet(wallet_id)
    }

}