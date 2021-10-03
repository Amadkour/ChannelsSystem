package com.example.androidtask.business.data.cache.room.channel

import androidx.room.*
import com.example.androidtask.business.domain.model.channel.CoverAsset
import com.example.androidtask.business.domain.model.channel.IconAsset
import com.example.androidtask.business.domain.model.channel.LatestMedia
import com.example.androidtask.business.domain.model.channel.Series
import com.google.gson.Gson

/*
  save channels in database at channel_table
 */

@Entity(tableName = "channel_table")
//----(support series and latest media lists in database)---//
@TypeConverters(SeriesList::class,LatestMediaList::class)
data class ChannelCacheEntity(
    @Embedded(prefix = "channel_") val cover_asset: CoverAsset= CoverAsset(),
    @Embedded(prefix = "channel_") val icon_asset: IconAsset=IconAsset(),
    @ColumnInfo(name = "channel_id") @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "channel_latest_media") val latestMedia: List<LatestMedia> = listOf(),
    @ColumnInfo(name = "channel_media_count") val mediaCount: Int=0,
    @ColumnInfo(name = "channel_series") val series: List<Series> = listOf(),
    @ColumnInfo(name = "channel_title") val title: String =""
)

//------------------(save series as a list in database)-------------------//
class SeriesList {
    @TypeConverter
    fun listToJson(value: List<Series>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Series>::class.java).toList()

}

//------------------(save latest media as a list in database)-------------//
class LatestMediaList {
    @TypeConverter
    fun listToJson(value: List<LatestMedia>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<LatestMedia>::class.java).toList()
}
