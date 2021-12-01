package com.example.fireapp.data.repositories

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

class RealtimePokemonRepository : PokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>> {
        TODO("Not yet implemented")
    }
}