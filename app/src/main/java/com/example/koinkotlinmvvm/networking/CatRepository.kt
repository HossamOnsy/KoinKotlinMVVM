package com.example.koinkotlinmvvm.networking

import com.example.koinkotlinmvvm.models.Cat
import retrofit2.Response

class CatRepository(private val catApi: CatApi) {

    fun getCatList(): Response<List<Cat>>? {
        /*
         We now go get list of cats from the API using our web service
         */
        return catApi.getCats(limit = 30).execute()
    }
}