package com.example.koinkotlinmvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainViewModel(private val catRepository: CatRepository) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val catsList = MutableLiveData<List<Cat>>()
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