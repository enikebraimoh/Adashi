package ng.adashi.network.retrofit


import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.payout.models.PayoutResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.SaversResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdashiApis {

    @POST("auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginToken

    @GET("transactions/me")
    suspend fun GetAgentTransactions() : AgentTransactionsResponse

    @GET("agent/savers")
    suspend fun GetAllSavers() : SaversResponse

    @GET("agent/balance")
    suspend fun GetAgentWallet() : AgentWalletDetails

    @POST("saver/saver")
    suspend fun AddSaver(
        @Body saver : SingleSaver
    ) : ng.adashi.ui.savers.addsaver.models.SaverResponse

    @POST("transactions/savings")
    suspend fun save(
        @Body save : SaveDetails
    ) : SaveResponse

    @POST("agent/payout")
    suspend fun payOut(
        @Body save : SaveDetails
    ) : PayoutResponse

}