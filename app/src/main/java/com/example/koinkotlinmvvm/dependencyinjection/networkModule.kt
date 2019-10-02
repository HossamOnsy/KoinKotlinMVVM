package com.example.koinkotlinmvvm.dependencyinjection


import com.example.koinkotlinmvvm.base.BaseViewModel
import com.example.koinkotlinmvvm.networking.CatApi
import com.example.koinkotlinmvvm.repository.CatRepository
import com.example.koinkotlinmvvm.repository.CatRepositoryImpl
import com.example.koinkotlinmvvm.viewmodel.MainViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

val appModules = module {

    single {
        createWebService<CatApi>(
            okHttpClient = createHttpClient(),
            factory1 = RxJava2CallAdapterFactory.create(),
            factory2 = CoroutineCallAdapterFactory(),
            baseUrl = CAT_API_BASE_URL
        )
    }
    // Tells Koin how to create an instance of CatRepository
    factory<CatRepository> {
        CatRepositoryImpl(
            catApi = get()
        )
    }
    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { MainViewModel(catRepository = get()) }

}

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

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory1: CallAdapter.Factory,
    factory2:CoroutineCallAdapterFactory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(factory1)
        .addCallAdapterFactory(factory2)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}
