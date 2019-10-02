package com.example.koinkotlinmvvm.networking

import com.example.koinkotlinmvvm.MainViewModel
import com.example.koinkotlinmvvm.utils.NetworkUtils.createWebService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

// this module is being used in our class that extends Application which will start Koin and allow dependency injection
// definition for dependency injection here -> https://en.wikipedia.org/wiki/Dependency_injection
val appModules = module {

    single { createWebService() }
    single { CatRepository(catApi = get()) }
    viewModel { MainViewModel(catRepository = get()) }

}

