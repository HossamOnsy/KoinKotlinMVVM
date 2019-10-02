package com.example.koinkotlinmvvm

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
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
        // we need to execute our network call in the background in order not to block our ui so we will use runnable
        // and execute it using AsyncTask
        val runnable = Runnable {
            val gettingCatListResponse = catRepository.getCatList()
            // now we need to get back from background thread so we will use Handler
            Handler(Looper.getMainLooper()).post {
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
        AsyncTask.execute(runnable)

    }

}