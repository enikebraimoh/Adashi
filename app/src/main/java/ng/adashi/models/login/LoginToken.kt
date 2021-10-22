package ng.adashi.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class LoginToken(
    @SerializedName("user")
    @Expose
    val user : AgentUser?,

    @SerializedName("accessToken")
    @Expose
    val accessToken: String,

    @SerializedName("refreshToken")
    @Expose
    val refreshToken: String
)