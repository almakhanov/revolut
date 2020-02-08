package com.revolut.exrate.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.revolut.exrate.BuildConfig
import com.revolut.exrate.data.remote.Api
import com.revolut.exrate.data.remote.CoroutineProvider
import com.revolut.exrate.data.interseptors.TokenInterceptor
import com.revolut.exrate.data.remote.OpenApi
import com.revolut.exrate.domain.repository.RateRepository
import com.revolut.exrate.presentation.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier


val appModule = module {

    /**
     * Api services
     */
    factory {
        createWebService<OpenApi>(
            get("openApi"),
            BuildConfig.API_URL
        )
    }
    factory {
        createWebService<Api>(
            get("default"),
            BuildConfig.API_URL
        )
    }

    /**
     * [OkHttpClient] instances
     */
    factory("default") { createOkHttpClient(androidContext(), get()) }
    factory("openApi") { createOkHttpOpenApi(androidContext()) }


    factory {
        CoroutineProvider()
    }

    factory {
        RateRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }
}

const val timeout = 120L

val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}

val hostnameVerifier = HostnameVerifier { _, _ -> true }

fun createOkHttpClient(context: Context, tokenInterceptor: TokenInterceptor) =
    OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .hostnameVerifier(hostnameVerifier)
        //.addInterceptor(HeaderInterceptor())
        .addInterceptor(tokenInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        //.sslSocketFactory(TLSSocketFactory.getSSLSocketFactory(context))
        .build()

fun createOkHttpOpenApi(context: Context) = OkHttpClient.Builder()
    .connectTimeout(timeout, TimeUnit.SECONDS)
    .readTimeout(timeout, TimeUnit.SECONDS)
    .hostnameVerifier(hostnameVerifier)
    //.addInterceptor(HeaderInterceptor())
    .addInterceptor(httpLoggingInterceptor)
    //.sslSocketFactory(TLSSocketFactory.getSSLSocketFactory(context))
    .build()

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
    return retrofit.create(T::class.java)
}