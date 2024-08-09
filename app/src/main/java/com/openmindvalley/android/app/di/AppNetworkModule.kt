package com.openmindvalley.android.app.di

import android.content.Context
import com.openmindvalley.android.app.data.network.ApiService
import com.openmindvalley.android.app.repository.MediaRepository
import com.openmindvalley.android.app.repository.MediaRepositoryImpl
import com.openmindvalley.android.app.utils.ApiConstant
import com.openmindvalley.android.app.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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
    fun provideApi(@ApplicationContext context: Context, loggerInterceptor: HttpLoggingInterceptor, networkUtils: NetworkUtils): ApiService {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (networkUtils.isInternetConnected)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
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