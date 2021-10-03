package com.example.androidtask.business.data.cache.room.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
  all processes can be performed in the category_table
 */

@Dao
interface CategoryDao {

    //---(inset a category object in database)---//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryCacheEntity: CategoryCacheEntity):Long

    //---(select all categories from database)---//
    @Query("select * from category_table")
    suspend fun getAllCacheCategory():List<CategoryCacheEntity>

    //---(delete all categories from database)---//
    @Query("DELETE FROM category_table")
    suspend fun deleteAllCacheCategory()

}