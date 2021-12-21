package ng.adashi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.adashi.network.NetworkDataSource
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.retrofit.AdashiApis
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {




}