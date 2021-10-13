package ng.adashi.network


import ng.adashi.models.login.LoginDetails
import ng.adashi.models.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdashiApis {

    @POST("api/v1/auth/login")
   suspend fun Login(
        @Body loginDetails : LoginDetails
   ) : Response<LoginResponse>


}