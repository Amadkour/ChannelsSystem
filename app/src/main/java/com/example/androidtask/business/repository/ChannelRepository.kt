package com.example.androidtask.business.repository

import com.example.androidtask.business.data.cache.room.channel.ChannelCacheEntity
import com.example.androidtask.business.data.cache.room.channel.ChannelDao
import com.example.androidtask.business.data.cache.room.channel.ChannelMapper
import com.example.androidtask.business.data.network.retrofit.ChannelRetrofit
import com.example.androidtask.business.domain.model.channel.Channel
import com.example.androidtask.business.domain.util.DataState
import com.example.androidtask.presentation.MainActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//-----------------------(Channel Repository class)----------------------//
class ChannelRepository constructor(
    private val channelDao: ChannelDao,
    private val channelRetrofit: ChannelRetrofit,
    private val channelMapper: ChannelMapper
) {
    //-----------------------(get all channels from Api then save in the database)----------------------//

    suspend fun getChannelFromApiAndSaveInDB(): Flow<DataState<List<Channel>>> =
        flow {
            if (MainActivity.online) {
                //---------------(save the loading state in the channel Flow)---------------//
                emit(DataState.Loading)
                try {
                    //---------------(get all channels from Api)---------------//
                    val networkChannelsString = channelRetrofit.getAllChannels()
                    //---------------(get channels value from  json)---------------//
                    val data: JsonElement =
                        Gson().fromJson(
                            Gson().toJson(networkChannelsString),
                            JsonElement::class.java
                        )
                    val channelData = data.asJsonObject.get("data").asJsonObject.get("channels")
                    //---------------(save channels value in a List)---------------//
                    val networkChannels: List<Channel> =
                        Gson().fromJson(
                            channelData,
                            Array<Channel>::class.java
                        ).asList()

                    //--------------(delete all  previous channels from DB)--------//
                    channelDao.deleteAllCacheChannel()

                    //--------------(save all new channels in DB)--------//
                    for (channel in networkChannels) {
                        channelDao.insert(
                            ChannelCacheEntity(
                                channel.coverAsset,
                                channel.iconAsset,
                                channel.id,
                                channel.latestMedia,
                                channel.mediaCount,
                                channel.series,
                                channel.title
                            )
                        )
                    }

                    //--------------(get all new channels from DB)--------//
                    val roomChannels = channelDao.getAllCacheChannel()
                    //---------(save all new channels in the channel Flow)---------//
                    emit(DataState.Success(channelMapper.mapFromEntitiesDB(roomChannels)))
                } catch (e: Exception) {

                    //---------(save error in the channel Flow)---------//
                    emit(DataState.Error(e))
                }
            } else {
                //--------------(get all new channels from DB)--------//
                val roomChannels = channelDao.getAllCacheChannel()
                //---------(save all new channels in the channel Flow)---------//
                emit(DataState.Success(channelMapper.mapFromEntitiesDB(roomChannels)))
            }
        }
}