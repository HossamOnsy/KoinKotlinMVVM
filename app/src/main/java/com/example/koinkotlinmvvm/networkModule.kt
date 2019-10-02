package com.example.koinkotlinmvvm

import com.example.koinkotlinmvvm.NetworkUtils.createHttpClient
import com.example.koinkotlinmvvm.NetworkUtils.createWebService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

// this module is being used in our class that extends Application which will start Koin and allow dependency injection
// definition for dependency injection here -> https://en.wikipedia.org/wiki/Dependency_injection
val appModules = module {

    // this creates instance of CatApi which will allow us to call our api and get our list
    single {
        // this is a method in a global object that we can access it's method and give it the required parameters
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

