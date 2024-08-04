package com.openmindvalley.android.app.di

import android.content.Context
import com.openmindvalley.android.app.data.network.ApiService
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.repository.MediaRepositoryImpl
import com.stocky.android.app.utils.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNetworkModule {

    @Provides
    @Singleton
    fun provideStockRepository(api: ApiService): MediaRepository {
        return MediaRepositoryImpl(api)
    }

    @Provides
    fun provideLoggerInterceptor() = HttpLoggingInterceptor()

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    @Singleton
    fun provideApi(loggerInterceptor: HttpLoggingInterceptor): ApiService {
        loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}