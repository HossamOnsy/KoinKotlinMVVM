package com.example.koinkotlinmvvm.networking

import com.example.koinkotlinmvvm.models.Cat
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    /* Get route used to retrieve cat images, limit is the number of cats item */
    @GET("images/search")
    fun getCats(@Query("limit") limit: Int)
            : Call<List<Cat>>
}