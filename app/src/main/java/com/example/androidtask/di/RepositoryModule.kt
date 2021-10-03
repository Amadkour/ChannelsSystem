package com.example.androidtask.di

import com.example.androidtask.business.repository.CategoryRepository
import com.example.androidtask.business.repository.ChannelRepository
import com.example.androidtask.business.repository.MediaRepository
import com.example.androidtask.business.data.network.retrofit.CategoryRetrofit
import com.example.androidtask.business.data.network.retrofit.ChannelRetrofit
import com.example.androidtask.business.data.network.retrofit.MediaRetrofit
import com.example.androidtask.business.data.cache.room.category.CategoryDao
import com.example.androidtask.business.data.cache.room.category.CategoryMapper
import com.example.androidtask.business.data.cache.room.category.MediaDao
import com.example.androidtask.business.data.cache.room.channel.ChannelDao
import com.example.androidtask.business.data.cache.room.channel.ChannelMapper
import com.example.androidtask.business.data.cache.room.media.MediaMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//-------------Repository creation with hilt injection---------------//

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    //-------------------(create Channel Repository by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideChannelRepository(
        channelDao: ChannelDao,
        channelRetrofit: ChannelRetrofit,
        channelMapper: ChannelMapper
    ): ChannelRepository {
        return ChannelRepository(
            channelDao,
            channelRetrofit,
            channelMapper
        )
    }

    //-------------------(create Category Repository by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryDao: CategoryDao,
        categoryRetrofit: CategoryRetrofit,
        categoryMapper: CategoryMapper
    ): CategoryRepository {
        return CategoryRepository(
            categoryDao,
            categoryRetrofit,
            categoryMapper
        )
    }

    //-------------------(create Media Repository by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideMediaRepository(
        mediaDao: MediaDao,
        mediaRetrofit: MediaRetrofit,
        mediaMapper: MediaMapper
    ): MediaRepository {
        return MediaRepository(
            mediaDao,
            mediaRetrofit,
            mediaMapper
        )
    }

}