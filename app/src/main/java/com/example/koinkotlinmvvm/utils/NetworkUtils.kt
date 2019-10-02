package com.example.koinkotlinmvvm.utils

import com.example.koinkotlinmvvm.networking.CatApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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


    fun createWebService(
        okHttpClient: OkHttpClient,
        factory1: CoroutineCallAdapterFactory, baseUrl: String
    ): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
                // GsonConverterFactory allows us to parse object form being a string into the class
            // we will provide in the interface which will start our request
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(factory1)
            .client(okHttpClient)
            .build()
        //CatApi is the class which contains our routes / Endpoint methods and queries that we need to have predefined
        return retrofit.create(CatApi::class.java)
    }
}
