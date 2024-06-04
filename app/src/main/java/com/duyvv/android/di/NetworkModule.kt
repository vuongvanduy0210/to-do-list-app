package com.duyvv.android.di

import com.duyvv.android.BuildConfig
import com.duyvv.android.network.api.AuthApi
import com.duyvv.android.network.api.EvaluationApi
import com.duyvv.android.network.api.RoomApi
import com.duyvv.android.network.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create()).baseUrl("BuildConfig.API_URL")
            .client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideEvaluationService(retrofit: Retrofit): EvaluationApi = retrofit.create(EvaluationApi::class.java)

    @Provides
    @Singleton
    fun provideRoomService2(retrofit: Retrofit): RoomApi = retrofit.create(RoomApi::class.java)
}
