package com.example.androidtask.business.data.cache.room.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  save the Category in database at category_table
 */

@Entity(tableName = "category_table")
data class CategoryCacheEntity(
    @ColumnInfo(name = "category_name") @PrimaryKey val name:String=""
    )
