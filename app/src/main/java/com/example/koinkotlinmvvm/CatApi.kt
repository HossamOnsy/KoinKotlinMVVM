package com.example.koinkotlinmvvm

import com.example.koinkotlinmvvm.Cat
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    /* Get route used to retrieve cat images, limit is the number of cats item */
    @GET("images/search")
    fun getCats(@Query("limit") limit: Int)
            : Deferred<List<Cat>>
}