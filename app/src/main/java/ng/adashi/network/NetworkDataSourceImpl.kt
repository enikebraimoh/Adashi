package ng.adashi.network

import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.savers.addsaver.models.SaverResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
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

    override suspend fun GetAgentWallet(): AgentWalletDetails {
        return retrofitApis.GetAgentWallet()
    }

    override suspend fun AddSaver(saver: SingleSaver): SaverResponse {
        return retrofitApis.AddSaver(saver = saver)
    }

    override suspend fun save(save: SaveDetails): SaveResponse {
        return retrofitApis.save(save = save)
    }

}