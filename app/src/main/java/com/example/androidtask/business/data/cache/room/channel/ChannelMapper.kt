package com.example.androidtask.business.data.cache.room.channel

import com.example.androidtask.business.domain.model.channel.Channel
import com.example.androidtask.business.domain.util.EntityMapper
import javax.inject.Inject

//------------------(Channel Mapper Class)---------------//

class ChannelMapper
@Inject
constructor() : EntityMapper<ChannelCacheEntity, Channel> {

    //------------------(map from Channel Database Entity into Channel class)---------------//
    override fun mapFromEntityDB(channelEntity: ChannelCacheEntity): Channel {
        return Channel(
            title = channelEntity.title,
            mediaCount = channelEntity.mediaCount,
            coverAsset = channelEntity.cover_asset,
            latestMedia = channelEntity.latestMedia,
            series = channelEntity.series,
            iconAsset = channelEntity.icon_asset
        )
    }

    //------------------(map from Channel Database Entity List into Channel class List)---------------//
    override fun mapFromEntitiesDB(channelCacheEntities: List<ChannelCacheEntity>): List<Channel> {
        return channelCacheEntities.map { mapFromEntityDB(it) }
    }
}