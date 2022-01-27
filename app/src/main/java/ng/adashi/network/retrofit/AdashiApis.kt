package ng.adashi.network.retrofit


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
import ng.adashi.ui.verification.docs.models.DocsInfoResponse
import ng.adashi.ui.verification.others.models.OthersInfo
import ng.adashi.ui.verification.others.models.OthersInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AdashiApis {

    @POST("auth/login")
    suspend fun Login(
        @Body loginDetails: LoginDetails
    ): LoginToken

    @POST("auth/signup")
    suspend fun signUp(
        @Body signUpDetails: SignUpData
    ): SignUpResponse

    @GET("transactions/me")
    suspend fun GetAgentTransactions(): AgentTransactionsResponse

    @GET("agent/savers")
    suspend fun GetAllSavers(): SaversResponse

    @GET("agent/balance")
    suspend fun GetAgentWallet(): AgentWalletDetails

    @POST("saver/saver")
    suspend fun AddSaver(
        @Body saver: SingleSaver
    ): ng.adashi.ui.savers.addsaver.models.SaverResponse

    @POST("transactions/savings")
    suspend fun save(
        @Body save: SaveDetails
    ): SaveResponse

    @POST("agent/payout")
    suspend fun payOut(
        @Body save: SaveDetails
    ): PayoutResponse

    @PATCH("agent/basic-info")
    suspend fun UpdateBasicInfo(
        @Body info: BasicInfo
    ): BasicInfoResponse

    @PATCH("agent/other-info")
    suspend fun updateOtherInfo(
        @Body info: OthersInfo
    ): OthersInfoResponse


    @Multipart
    @PATCH("agent/doc-info")
    suspend fun uploadDocs(
        @Part ("idType") idType : RequestBody,
        @Part ("idNumber") idNumber : RequestBody,
        @Part  idFile : MultipartBody.Part,
        @Part  billFile : MultipartBody.Part,
        @Part  signatureFile : MultipartBody.Part,
    ): DocsInfoResponse


}