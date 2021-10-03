package com.example.androidtask.business.data.cache.room.media

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtask.business.domain.model.channel.CoverAsset
import com.example.androidtask.business.domain.model.media.MediaChannel

/*
  save the Media in database at media_table
 */
@Entity(tableName = "media_table")
data class MediaCacheEntity(
    @ColumnInfo(name = "media_title") @PrimaryKey val title: String = "",
    @Embedded(prefix = "media_") val channel: MediaChannel = MediaChannel(),
    @Embedded(prefix = "media_") val cover_asset: CoverAsset = CoverAsset(),
    @ColumnInfo(name = "media_type") val type: String = ""
)