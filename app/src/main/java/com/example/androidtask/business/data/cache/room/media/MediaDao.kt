package com.example.androidtask.business.data.cache.room.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtask.business.data.cache.room.media.MediaCacheEntity

/*
   all processes can be performed in the media_table
 */

@Dao
interface MediaDao {

    //---(inset a media object in database)---//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaCacheEntity: MediaCacheEntity):Long

    //---(select all media objects from database)---//
    @Query("select * from media_table")
    suspend fun getAllCacheMedia():List<MediaCacheEntity>

    //---(delete all media objects from database)---//
    @Query("DELETE FROM media_table")
    suspend fun deleteAllCacheMedia()

}