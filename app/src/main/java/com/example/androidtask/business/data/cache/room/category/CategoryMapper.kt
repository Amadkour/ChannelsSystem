package com.example.androidtask.business.data.cache.room.category

import com.example.androidtask.business.domain.model.category.Category
import com.example.androidtask.business.domain.util.EntityMapper
import javax.inject.Inject

//------------------(Category Mapper Class)---------------//
class CategoryMapper
@Inject
constructor() : EntityMapper<CategoryCacheEntity, Category> {

    //------------------(map from Category Database Entity into Category class)---------------//
    override fun mapFromEntityDB(categoryEntity: CategoryCacheEntity): Category {
        return Category(
            name = categoryEntity.name,
        )
    }

    //------------------(map from Category Database Entity List into Category class List)---------------//
    override fun mapFromEntitiesDB(categoryCacheEntities: List<CategoryCacheEntity>): List<Category> {
        return categoryCacheEntities.map { mapFromEntityDB(it) }
    }
}