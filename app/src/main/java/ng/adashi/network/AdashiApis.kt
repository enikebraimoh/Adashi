package ng.adashi.network


import ng.adashi.domain_models.login.LoginDetails
import ng.adashi.domain_models.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AdashiApis {

    @POST("api/v1/auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : LoginResponse


}