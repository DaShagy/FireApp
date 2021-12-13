package com.example.fireapp.data.mappers

import com.example.fireapp.data.database.room.entities.RoomPokemon
import com.example.fireapp.domain.entities.Pokemon

class RoomPokemonMapperRepository : BaseMapperRepository<Pokemon, RoomPokemon> {
    override fun transform(type: Pokemon): RoomPokemon =
        RoomPokemon(
            id = type.id ?: -1,
            name = type.name ?: "",
            order = type.order ?: -1,
            height = type.height ?: -1,
            weight = type.weight ?: -1
        )

    override fun transformToRepository(type: RoomPokemon): Pokemon =
        Pokemon(
            id = type.id,
            name = type.name,
            order = type.order,
            height = type.height,
            weight = type.weight
        )
}