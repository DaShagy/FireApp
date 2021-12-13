package com.example.fireapp.data.repositories

import com.example.fireapp.data.database.room.daos.PokemonDao
import com.example.fireapp.data.mappers.RoomPokemonMapperRepository
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.RoomPokemonRepository
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RoomPokemonRepositoryImpl(
    private val mapper: RoomPokemonMapperRepository,
    private val dao: PokemonDao
) : RoomPokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) =
        dao.insertPokemon(
            mapper.transform(pokemon)
        )

    override suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>> =
        flow {
            dao.getAllPokemon().collect { pokemonList ->
                pokemonList.map {  mapper.transformToRepository(it) }
                    .also {
                        emit(ResultWrapper.Success(it))
                    }
            }
        }

    override suspend fun deletePokemon(pokemon: Pokemon) =
        dao.deletePokemon(
            mapper.transform(pokemon)
        )
}