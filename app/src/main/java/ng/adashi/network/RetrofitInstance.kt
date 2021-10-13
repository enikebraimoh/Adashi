package ng.adashi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ng.adashi.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

   private val retrofit by lazy {
       Retrofit.Builder()
           .addConverterFactory(MoshiConverterFactory.create(moshi))
           .baseUrl(BASE_URL)
           .build()
   }

    val Api : AdashiApis by lazy {
        retrofit.create(AdashiApis::class.java)
    }

}