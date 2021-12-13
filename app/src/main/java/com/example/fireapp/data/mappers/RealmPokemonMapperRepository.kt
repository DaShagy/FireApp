package com.example.fireapp.data.mappers

import com.example.fireapp.data.database.realm.entities.RealmPokemon
import com.example.fireapp.domain.entities.Pokemon

class RealmPokemonMapperRepository : BaseMapperRepository<Pokemon, RealmPokemon> {
    override fun transform(type: Pokemon): RealmPokemon =
        RealmPokemon(
            id = type.id ?: -1,
            name = type.name ?: "",
            order = type.order ?: -1,
            height = type.height ?: -1,
            weight = type.weight ?: -1
        )

    override fun transformToRepository(type: RealmPokemon): Pokemon =
        Pokemon(
            id = type.id,
            name = type.name,
            order = type.order,
            height = type.height,
            weight = type.weight
        )
}