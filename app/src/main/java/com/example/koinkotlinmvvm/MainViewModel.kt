package com.example.koinkotlinmvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koinkotlinmvvm.models.Cat
import com.example.koinkotlinmvvm.networking.CatRepository

// Koin will inject catRepositoryImpl in runtime using get() that's why it's not appearing in MainActivity
// and you can check it in networkModule.kt
class MainViewModel(private val catRepository: CatRepository) : ViewModel() {

    val catListRetrievedSuccessfully = MutableLiveData<List<Cat>>()
    val exceptionMessageReceived = MutableLiveData<String>()

    fun loadCats() {
        val gettingCatListResponse = catRepository.getCatList()
        if (gettingCatListResponse?.body() != null && gettingCatListResponse.isSuccessful) {
            catListRetrievedSuccessfully.value = gettingCatListResponse.body()
        } else {
            try {
                exceptionMessageReceived.value = gettingCatListResponse!!.message()
            } catch (e: Exception) {
                exceptionMessageReceived.value = e.message
            }
        }
    }

}