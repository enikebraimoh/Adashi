package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import ng.adashi.ui.home.models.AgentWalletResponse

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginResponse
    suspend fun GetWallet(wallet_id : String) : AgentWalletResponse

}