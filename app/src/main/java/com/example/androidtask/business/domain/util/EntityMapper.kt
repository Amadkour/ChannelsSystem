package com.example.androidtask.business.domain.util

//--------------------(Mapper Interface)------------------//
interface EntityMapper<EntityDB, DomainClass> {
    fun mapFromEntityDB(entity: EntityDB):DomainClass
    fun mapFromEntitiesDB(entities: List<EntityDB>):List<DomainClass>
}