package ng.adashi.network

import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.savers.models.SaversResponse
import javax.inject.Inject

class NetworkDataSourceImpl
@Inject
constructor (private val retrofitApis : AdashiApis) : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails):  LoginToken {
           return retrofitApis.Login(loginDetails)
    }

    override suspend fun GetAgentTransactions(): AgentTransactionsResponse {
        return retrofitApis.GetAgentTransactions()
    }

    override suspend fun GetAllSavers(): SaversResponse {
        return retrofitApis.GetAllSavers()
    }

    override suspend fun GetWallet(wallet_id: String): AgentWalletResponse {
     return retrofitApis.GetAgentWallet(wallet_id)
    }

}