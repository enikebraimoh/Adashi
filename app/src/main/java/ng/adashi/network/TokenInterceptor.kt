package ng.adashi.network

import android.content.Context
import ng.adashi.utils.App
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.header("No-Authentication") == null) {
            if (!App.token.isNullOrEmpty()) {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer ${App.token}")
                    .build()
            }
        }
        return chain.proceed(request)
    }

}