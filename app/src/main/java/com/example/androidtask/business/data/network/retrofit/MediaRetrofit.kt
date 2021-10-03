package com.example.androidtask.business.data.network.retrofit

import retrofit2.http.GET

interface MediaRetrofit {

    //---------------(get all Media objects from API)--------------//
    @GET("z5AExTtw")
    suspend fun getAllMediaObjects(): Any
}