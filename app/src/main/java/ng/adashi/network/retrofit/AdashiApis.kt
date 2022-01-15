package ng.adashi.network.retrofit


import ng.adashi.domain_models.agent.AgentWalletDetails
import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginToken
import ng.adashi.ui.home.models.transactions.AgentTransactionsResponse
import ng.adashi.ui.home.models.wallet.AgentWalletResponse
import ng.adashi.ui.makesavings.models.SaveDetails
import ng.adashi.ui.makesavings.models.SaveResponse
import ng.adashi.ui.savers.addsaver.models.SingleSaver
import ng.adashi.ui.savers.models.SaversResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdashiApis {

    @POST("api/v1/auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginToken

    @GET("api/v1/transactions/me")
    suspend fun GetAgentTransactions() : AgentTransactionsResponse

    @GET("api/v1/agent/savers")
    suspend fun GetAllSavers() : SaversResponse

    @GET("api/v1/agent/balance")
    suspend fun GetAgentWallet() : AgentWalletDetails

    @POST("api/v1/saver/saver")
    suspend fun AddSaver(
        @Body saver : SingleSaver
    ) : ng.adashi.ui.savers.addsaver.models.SaverResponse

    @POST("api/v1/transactions/savings")
    suspend fun save(
        @Body save : SaveDetails
    ) : SaveResponse

}