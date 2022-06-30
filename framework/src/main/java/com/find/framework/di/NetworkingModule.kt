package com.find.framework.di

import android.content.Context
import com.find.framework.remote.AuthInterceptor
import com.find.framework.remote.api.PoiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@OptIn(ExperimentalSerializationApi::class)
@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideAPIToken(): String = "1e2cb9c6-a0e9-4a68-bc09-f3c97a6bd8e4"

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: AuthInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openchargemap.io/v3/")
            .addConverterFactory(
                Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                }
                    .asConverterFactory(MediaType.get("application/json"))
            )
            .client(client)
            .build()

    @Provides
    @Singleton
    fun getPoiApi(retrofit: Retrofit): PoiService =
        retrofit.create(PoiService::class.java)


}