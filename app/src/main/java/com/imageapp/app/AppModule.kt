package com.imageapp.app

import android.content.Context
import com.imageapp.BuildConfig
import com.imageapp.utils.Constants
import com.imageapp.api.ApiHelper
import com.imageapp.api.ApiHelperImpl
import com.imageapp.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


    private class SessionCookieJar : CookieJar {
        private var cookies: List<Cookie>? = null
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            if (url.encodedPath.endsWith("login")) {
                this.cookies = ArrayList(cookies)
            }
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return if (!url.encodedPath.endsWith("login") && cookies != null) {
                return cookies!!
            } else Collections.emptyList()
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context
    ) =
        run {
            val cookieManager = CookieManager()

            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)


            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original: Request = chain.request()

                    val request: Request = original.newBuilder()
                        // .header("Authorization", "Bearer $token")
                        .method(original.method, original.body)
                        .build()

                    val response: Response = chain.proceed(request)



                    response
                }

                .also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                    client.connectTimeout(30, TimeUnit.SECONDS)
                    client.readTimeout(30, TimeUnit.SECONDS)
                    client.writeTimeout(30, TimeUnit.SECONDS)
                    client.cookieJar(SessionCookieJar()).build()
                }.build()
        }
}