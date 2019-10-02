package com.example.koinkotlinmvvm.utils

import com.example.koinkotlinmvvm.networking.CAT_API_BASE_URL
import com.example.koinkotlinmvvm.networking.CatApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {

    // OkHttpClient allows us to Log using OkHttp and see what happens in our requests and how we send and retreive in the network layer
    fun createHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return client.addInterceptor(interceptor).build()
    }

    fun createWebService(): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(CAT_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
        return retrofit.create(CatApi::class.java)
    }
}
