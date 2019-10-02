package com.example.koinkotlinmvvm.networking

import com.example.koinkotlinmvvm.models.Cat
import retrofit2.Response

class CatRepository(private val catApi: CatApi) {

    fun getCatList(): Response<List<Cat>>? {
        /*
         We try to return a list of cats from the API
         Await the result from web service and then return it, catching any error from API
         */
        return catApi.getCats(limit = 30).execute()
    }
}