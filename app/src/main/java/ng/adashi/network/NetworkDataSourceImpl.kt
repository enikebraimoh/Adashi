package ng.adashi.network

import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.network.retrofit.*
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.payout.models.PayoutResponse
import ng.adashi.ui.savers.addsaver.models.SaverResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.SaversResponse
import ng.adashi.ui.signup.models.SignUpData
import ng.adashi.ui.signup.models.SignUpResponse
import ng.adashi.ui.verification.basic.models.BasicInfo
import ng.adashi.ui.verification.basic.models.BasicInfoResponse
import ng.adashi.ui.verification.others.models.OthersInfo
import ng.adashi.ui.verification.others.models.OthersInfoResponse
import javax.inject.Inject

class NetworkDataSourceImpl
@Inject
constructor (private val retrofitApis : AdashiApis) : NetworkDataSource {

    override suspend fun login(loginDetails: LoginDetails):  LoginToken {
           return retrofitApis.Login(loginDetails)
    }

    override suspend fun signUp(signUpDetails: SignUpData): SignUpResponse {
        return retrofitApis.signUp(signUpDetails)
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

    override suspend fun payout(save: SaveDetails): PayoutResponse {
        return retrofitApis.payOut(save)
    }

    override suspend fun UpdateBasicInfo(info: BasicInfo): BasicInfoResponse {
        return retrofitApis.UpdateBasicInfo(info)
    }

    override suspend fun updateOthersInfo(info: OthersInfo): OthersInfoResponse {
        return retrofitApis.updateOtherInfo(info)
    }

}