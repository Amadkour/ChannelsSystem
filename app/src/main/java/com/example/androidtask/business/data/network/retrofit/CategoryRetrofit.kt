package com.example.androidtask.business.data.network.retrofit

import retrofit2.http.GET

interface CategoryRetrofit {

    //---------------(get all category objects from API)--------------//
    @GET("A0CgArX3")
    suspend fun getAllCategories(): Any
}