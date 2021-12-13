package com.example.fireapp.data.mappers

interface BaseMapperRepository<E, D> {
    fun transform(type: E): D
    fun transformToRepository(type: D): E
}