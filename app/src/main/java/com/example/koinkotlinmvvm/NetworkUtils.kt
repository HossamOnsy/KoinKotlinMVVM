package com.example.koinkotlinmvvm

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkUtils {
    fun createHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.readTimeout(5 * 60, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.method(original.method(), original.body()).build()
            return@addInterceptor it.proceed(request)
        }.addInterceptor(interceptor).build()
    }

    fun createWebService(
        okHttpClient: OkHttpClient,
        factory1: CoroutineCallAdapterFactory, baseUrl: String
    ): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(factory1)
            .client(okHttpClient)
            .build()
        return retrofit.create(CatApi::class.java)
    }
}
