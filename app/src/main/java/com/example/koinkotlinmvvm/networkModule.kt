package com.example.koinkotlinmvvm

import com.example.koinkotlinmvvm.NetworkUtils.createHttpClient
import com.example.koinkotlinmvvm.NetworkUtils.createWebService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

val appModules = module {

    single {
        createWebService(
            okHttpClient = createHttpClient(),
            factory1 = CoroutineCallAdapterFactory(),
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

