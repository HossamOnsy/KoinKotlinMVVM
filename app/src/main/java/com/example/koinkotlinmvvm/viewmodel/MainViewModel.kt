package com.example.koinkotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.koinkotlinmvvm.base.BaseViewModel
import com.example.koinkotlinmvvm.models.Cat
import com.example.koinkotlinmvvm.models.UseCaseResult
import com.example.koinkotlinmvvm.repository.CatRepository
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class MainViewModel(private val catRepository: CatRepository) : BaseViewModel(), CoroutineScope {
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
                    Timber.d("TestTestTest is -> ${result.exception.message}")
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