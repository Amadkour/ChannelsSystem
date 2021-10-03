package com.example.androidtask.business.data.cache.room.media

import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.business.domain.util.EntityMapper
import javax.inject.Inject

//------------------(Media Mapper Class)---------------//
class MediaMapper
@Inject
constructor() : EntityMapper<MediaCacheEntity, Media> {

    //------------------(map from Media Database Entity into Media class)---------------//
    override fun mapFromEntityDB(mediaEntity: MediaCacheEntity): Media {
        return Media(
            mediaChannel = mediaEntity.channel,
            coverAsset = mediaEntity.cover_asset,
            type = mediaEntity.type,
            title = mediaEntity.title,
        )
    }

    //------------------(map from Media Database Entity List into Media class List)---------------//
    override fun mapFromEntitiesDB(mediaCacheEntities: List<MediaCacheEntity>): List<Media> {
        return mediaCacheEntities.map { mapFromEntityDB(it) }
    }
}