package ng.adashi.network.retrofit

import ng.adashi.network.TokenInterceptor
import ng.adashi.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitInstance {

    private var client = OkHttpClient.Builder().apply {
        addInterceptor(TokenInterceptor())
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    val api: AdashiApis by lazy {
        retrofit.create(AdashiApis::class.java)
    }

}