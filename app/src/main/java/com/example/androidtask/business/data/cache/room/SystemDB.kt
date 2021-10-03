package com.example.androidtask.business.data.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtask.business.data.cache.room.category.CategoryCacheEntity
import com.example.androidtask.business.data.cache.room.category.CategoryDao
import com.example.androidtask.business.data.cache.room.category.MediaDao
import com.example.androidtask.business.data.cache.room.channel.ChannelCacheEntity
import com.example.androidtask.business.data.cache.room.channel.ChannelDao
import com.example.androidtask.business.data.cache.room.media.MediaCacheEntity

//---------------------(Database Class)-----------------//
@Database(entities = [ChannelCacheEntity::class,CategoryCacheEntity::class,MediaCacheEntity::class], version = 1)
abstract class SystemDB : RoomDatabase() {

    //----------(Channel Data Access Object)---------//
    abstract fun channelDao(): ChannelDao

    //----------(Category Data Access Object)---------//
    abstract fun categoryDao(): CategoryDao

    //-------------(Channel Data Access Object)------//
    abstract fun mediaDao(): MediaDao

    //---------------------(database name)------------//
    companion object {
        const val databaseName: String = "channels_DB"
    }

}