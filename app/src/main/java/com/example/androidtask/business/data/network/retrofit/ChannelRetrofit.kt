package com.example.androidtask.business.data.network.retrofit

import retrofit2.http.GET

interface ChannelRetrofit {

    //---------------(get all channel object from API)--------------//
    @GET("Xt12uVhM")
    suspend fun getAllChannels(): Any
}