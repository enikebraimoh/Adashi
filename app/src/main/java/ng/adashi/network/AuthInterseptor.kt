package ng.adashi.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterseptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Bearer-Token","")
            .build()
        return chain.proceed(request)
    }

}