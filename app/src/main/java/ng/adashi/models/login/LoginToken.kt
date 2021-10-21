package ng.adashi.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class LoginToken(

    @SerializedName("accessToken")
    @Expose
    val accessToken: String,

    @SerializedName("refreshToken")
    @Expose
    val refreshToken: String,

    @SerializedName("user")
    @Expose
    @Json(name = "user")
    val user : AgentUser? = null
)