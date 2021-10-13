package ng.adashi.models.login

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message: String,
    @Json(name = "data")
    val data: LoginToken
)