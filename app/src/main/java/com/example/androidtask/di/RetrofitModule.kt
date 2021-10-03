package com.example.androidtask.di

import com.example.androidtask.business.data.network.retrofit.CategoryRetrofit
import com.example.androidtask.business.data.network.retrofit.ChannelRetrofit
import com.example.androidtask.business.data.network.retrofit.MediaRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//-------------API retrofit creation with hilt injection---------------//
@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    //-------------------(create Gson object by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    //-------------------( create retrofit builder by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://pastebin.com/raw/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    //-------------------(create Category retrofit by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideChannelService(retrofit:Retrofit.Builder): ChannelRetrofit {
        return retrofit.build().create(ChannelRetrofit::class.java)
    }

    //-------------------(create Category retrofit by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideCategoryService(retrofit:Retrofit.Builder): CategoryRetrofit {
        return retrofit.build().create(CategoryRetrofit::class.java)
    }

    //-------------------(create Category retrofit by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideMediaService(retrofit:Retrofit.Builder): MediaRetrofit {
        return retrofit.build().create(MediaRetrofit::class.java)
    }

}