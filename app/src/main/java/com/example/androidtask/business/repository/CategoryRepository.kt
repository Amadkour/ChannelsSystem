package com.example.androidtask.business.repository

import com.example.androidtask.business.data.cache.room.category.CategoryCacheEntity
import com.example.androidtask.business.data.cache.room.category.CategoryDao
import com.example.androidtask.business.data.cache.room.category.CategoryMapper
import com.example.androidtask.business.data.network.retrofit.CategoryRetrofit
import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.domain.util.DataState
import com.example.androidtask.presentation.MainActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//-----------------------(Channel Repository class)----------------------//
class CategoryRepository constructor(
    private val categoryDao: CategoryDao,
    private val categoryRetrofit: CategoryRetrofit,
    private val categoryMapper: CategoryMapper,

    ) {
    //-----------------------(get all channels from Api then save in the database)----------------------//

    suspend fun getCategoryFromApiAndSaveInDB(): Flow<DataState<List<Category>>> =
        flow {
            if (MainActivity.online) {
                //---------------(save the loading state in the channel Flow)---------------//
                emit(DataState.Loading)
                try {
                    //---------------(get all channels from Api)---------------//

                    val networkCategoriesString = categoryRetrofit.getAllCategories()

                    //---------------(get channels value from  json)---------------//
                    val data: JsonElement =
                        Gson().fromJson(
                            Gson().toJson(networkCategoriesString),
                            JsonElement::class.java
                        )
                    val categoryData = data.asJsonObject.get("data").asJsonObject.get("categories")

                    //---------------(save channels value in a List)---------------//
                    val networkCategories: List<Category> =
                        Gson().fromJson(
                            categoryData,
                            Array<Category>::class.java
                        ).asList()

                    //--------------(delete all  previous channels from DB)--------//
                    categoryDao.deleteAllCacheCategory()

                    //--------------(save all new channels in DB)--------//
                    for (category in networkCategories) {
                        categoryDao.insert(
                            CategoryCacheEntity(
                                category.name
                            )
                        )
                    }
                    //--------------(get all new channels from DB)--------//
                    val roomCategories = categoryDao.getAllCacheCategory()
                    //---------(save all new channels in the channel Flow)---------//
                    emit(DataState.Success(categoryMapper.mapFromEntitiesDB(roomCategories)))

                } catch (e: Exception) {

                    //---------(save error in the channel Flow)---------//
                    emit(DataState.Error(e))
                }
            } else {
                //--------------(get all new channels from DB)--------//
                val roomCategories = categoryDao.getAllCacheCategory()
                //---------(save all new channels in the channel Flow)---------//
                emit(DataState.Success(categoryMapper.mapFromEntitiesDB(roomCategories)))

            }
        }
}