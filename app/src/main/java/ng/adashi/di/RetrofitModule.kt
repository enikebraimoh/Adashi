package ng.adashi.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ng.adashi.network.NetworkDataSource
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.network.TokenInterceptor
import ng.adashi.network.retrofit.AdashiApis
import ng.adashi.network.retrofit.RetrofitInstance
import ng.adashi.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun providesInterceptors() : OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(TokenInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(client :OkHttpClient , gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun providesApiService (retrofit :  Retrofit.Builder) : AdashiApis{
        return retrofit.build().create(AdashiApis::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(api : AdashiApis) : NetworkDataSource {
        return NetworkDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) : SessionManager {
        return SessionManager(context)
    }


}