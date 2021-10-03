package com.example.androidtask.business.repository

import com.example.androidtask.business.data.cache.room.category.MediaDao
import com.example.androidtask.business.data.cache.room.media.MediaCacheEntity
import com.example.androidtask.business.data.cache.room.media.MediaMapper
import com.example.androidtask.business.data.network.retrofit.MediaRetrofit
import com.example.androidtask.business.domain.model.media.Media
import com.example.androidtask.business.domain.util.DataState
import com.example.androidtask.presentation.MainActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//-----------------------(Channel Repository class)----------------------//
class MediaRepository constructor(
    private val mediaDao: MediaDao,
    private val mediaRetrofit: MediaRetrofit,
    private val mediaMapper: MediaMapper,

    ) {
    //-----------------------(get all Media from Api then save in the database)----------------------//

    suspend fun getMediaFromApiAndSaveInDB(): Flow<DataState<List<Media>>> =
        flow {
            if (MainActivity.online) {
                //---------------(save the loading state in the Media  Flow)---------------//
                emit(DataState.Loading)
                try {
                    //---------------(get all Media objects from Api)---------------//
                    val networkMediaString = mediaRetrofit.getAllMediaObjects()

                    //---------------(get Media objects value from  json)---------------//
                    val data: JsonElement =
                        Gson().fromJson(Gson().toJson(networkMediaString), JsonElement::class.java)
                    val mediaData = data.asJsonObject.get("data").asJsonObject.get("media")

                    //---------------(save Media objects value in a List)---------------//
                    val networkMedia: List<Media> =
                        Gson().fromJson(
                            mediaData,
                            Array<Media>::class.java
                        ).asList()

                    //--------------(delete all  previous Media objects from DB)--------//
                    mediaDao.deleteAllCacheMedia()

                    //--------------(save all new Media objects in DB)--------//
                    for (media in networkMedia) {
                        mediaDao.insert(
                            MediaCacheEntity(
                                media.title,
                                media.mediaChannel,
                                media.coverAsset,
                                media.type
                            )
                        )
                    }
                    //--------------(get all new Media objects from DB)--------//
                    val roomMedia = mediaDao.getAllCacheMedia()
                    //---------(save all new Media objects in the channel Flow)---------//
                    emit(DataState.Success(mediaMapper.mapFromEntitiesDB(roomMedia)))
                } catch (e: Exception) {

                    //---------(save error in the Media  Flow)---------//
                    emit(DataState.Error(e))
                }
            } else {
                //--------------(get all new Media objects from DB)--------//
                val roomMedia = mediaDao.getAllCacheMedia()
                //---------(save all new Media objects in the channel Flow)---------//
                emit(DataState.Success(mediaMapper.mapFromEntitiesDB(roomMedia)))
            }
        }
}