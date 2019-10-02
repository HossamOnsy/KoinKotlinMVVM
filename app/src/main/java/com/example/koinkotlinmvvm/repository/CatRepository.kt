package com.example.koinkotlinmvvm.repository

import com.example.koinkotlinmvvm.models.Cat
import com.example.koinkotlinmvvm.models.UseCaseResult
import com.example.koinkotlinmvvm.networking.CatApi

interface CatRepository {
    // Suspend is used to await the result from Deferred
   suspend fun getCatList(): UseCaseResult<List<Cat>>
}

class CatRepositoryImpl(private val catApi: CatApi) :
    CatRepository {

    override suspend fun getCatList(): UseCaseResult<List<Cat>> {
        /*
         We try to return a list of cats from the API
         Await the result from web service and then return it, catching any error from API
         */
        return try {
            val result = catApi.getCats(limit = 30).await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }

    }
}