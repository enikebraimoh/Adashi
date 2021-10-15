package ng.adashi.models.login

import com.squareup.moshi.Json

data class LoginToken(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "user")
    val user : AgentUser? = null
)