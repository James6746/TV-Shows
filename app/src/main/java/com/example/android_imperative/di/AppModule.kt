package com.example.android_imperative.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.android_imperative.network.Server.IS_TESTER
import com.example.android_imperative.network.Server.SERVER_DEPLOYMENT
import com.example.android_imperative.network.Server.SERVER_DEVELOPMENT
import com.example.android_imperative.network.TVShowService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Retrofit Related
    @Provides
    fun server() = if (IS_TESTER) SERVER_DEVELOPMENT else SERVER_DEPLOYMENT

    @Provides
    @Singleton
    fun retrofitClient() =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun tvShowService() = retrofitClient().create(TVShowService::class.java)

    //Room related


}