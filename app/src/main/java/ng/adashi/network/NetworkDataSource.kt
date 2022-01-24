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
import ng.adashi.ui.signup.models.SignUpData
import ng.adashi.ui.signup.models.SignUpResponse
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.ui.verification.others.models.OthersInfo
import ng.adashi.ui.verification.others.models.OthersInfoResponse

interface NetworkDataSource {

    suspend fun login(loginDetails: LoginDetails) : LoginToken
    suspend fun signUp(signUpDetails: SignUpData) : SignUpResponse

    suspend fun GetAgentTransactions() : AgentTransactionsResponse
    suspend fun GetAllSavers() : SaversResponse

    suspend fun GetAgentWallet() : AgentWalletDetails
    suspend fun AddSaver(saver : SingleSaver) : ng.adashi.ui.savers.addsaver.models.SaverResponse
    suspend fun save(save : SaveDetails) : SaveResponse
    suspend fun payout(save : SaveDetails) : PayoutResponse
    suspend fun UpdateBasicInfo(info : BasicInfo) : BasicInfoResponse
    suspend fun updateOthersInfo(info : OthersInfo) : OthersInfoResponse
}