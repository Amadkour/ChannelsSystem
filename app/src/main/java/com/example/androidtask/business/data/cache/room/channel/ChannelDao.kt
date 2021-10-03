package com.example.androidtask.business.data.cache.room.channel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/*
   all processes can be performed in the channel_table
 */

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelCacheEntity: ChannelCacheEntity):Long
    @Query("select * from channel_table")
    suspend fun getAllCacheChannel():List<ChannelCacheEntity>

    @Query("DELETE FROM channel_table")
    suspend fun deleteAllCacheChannel()

}