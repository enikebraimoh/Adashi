package ng.adashi.models.login

import com.squareup.moshi.Json

data class LoginDetails(
    val email: String,
    val password: String
)