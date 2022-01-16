package ng.adashi.network

import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.payout.models.PayoutResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.SaversResponse
import retrofit2.http.Body

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun GetAgentTransactions() : AgentTransactionsResponse
    suspend fun GetAllSavers() : SaversResponse

    suspend fun GetAgentWallet() : AgentWalletDetails
    suspend fun AddSaver(saver : SingleSaver) : ng.adashi.ui.savers.addsaver.models.SaverResponse
    suspend fun save(save : SaveDetails) : SaveResponse
    suspend fun payout(save : SaveDetails) : PayoutResponse



}