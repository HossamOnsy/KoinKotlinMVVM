package com.example.koinkotlinmvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koinkotlinmvvm.models.Cat
import com.example.koinkotlinmvvm.models.UseCaseResult
import com.example.koinkotlinmvvm.networking.CatRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainViewModel(private val catRepository: CatRepository) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    // MutableLiveData is a type of class that allows you to observe when a value is set to it
    // this allows to initiate loading for user experience
    val showLoading = MutableLiveData<Boolean>()
    // this allows to listen to the catslist when we get a list from our request
    val catsList = MutableLiveData<List<Cat>>()
    // this allows to listen to errors and observe on exceptions to show them for user
    val showError = MutableLiveData<String>()

    fun loadCats() {
        // Show progressBar during the operation on the MAIN (default) thread
        showLoading.value = true
        // launch the Coroutine
        launch {
            // Switching from MAIN to IO thread for API operation
            // Update our data list with the new one from API
            val result = withContext(Dispatchers.IO) { catRepository.getCatList() }
            // Hide progressBar once the operation is done on the MAIN (default) thread
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> catsList.value = result.data
                is UseCaseResult.Error -> {
                    Log.v("TestTestTest", result.exception.message)
                    showError.value = result.exception.message
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }
}