package com.example.androidtask.di

import android.content.Context
import androidx.room.Room
import com.example.androidtask.business.data.cache.room.SystemDB
import com.example.androidtask.business.data.cache.room.category.CategoryDao
import com.example.androidtask.business.data.cache.room.category.MediaDao
import com.example.androidtask.business.data.cache.room.channel.ChannelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//-------------database creation with hilt injection---------------//

@InstallIn(SingletonComponent::class)
@Module
object RoomChannelModule {

    //-------------------(create database by hilt Injection)-------------//
    @Singleton
    @Provides
    fun provideSystemDB(@ApplicationContext context: Context): SystemDB {
        return Room.databaseBuilder(
            context,
            SystemDB::class.java,
            SystemDB.databaseName
        ).fallbackToDestructiveMigration()
            .build()
    }

    //-----------(create Channel DAO by hilt Injection)-----------//
    @Singleton
    @Provides
    fun provideChannelDao(systemDb: SystemDB): ChannelDao {
        return systemDb.channelDao()
    }

    //-----------(create Category DAO by hilt Injection)-----------//
    @Singleton
    @Provides
    fun provideCategoryDao(systemDb: SystemDB): CategoryDao {
        return systemDb.categoryDao()
    }

    //-----------(create Channel DAO by hilt Injection)-----------//
    @Singleton
    @Provides
    fun provideMediaDao(systemDb: SystemDB): MediaDao {
        return systemDb.mediaDao()
    }

}