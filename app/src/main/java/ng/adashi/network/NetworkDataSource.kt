package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.savers.models.SaversResponse

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun GetAgentTransactions() : AgentTransactionsResponse
    suspend fun GetAllSavers() : SaversResponse


    suspend fun GetWallet(wallet_id : String) : AgentWalletResponse

}