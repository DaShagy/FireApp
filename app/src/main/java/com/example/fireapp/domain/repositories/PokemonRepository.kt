package com.example.fireapp.domain.repositories

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun insertPokemon(pokemon: Pokemon)
    suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>>
}

interface FirestorePokemonRepository : PokemonRepository
interface RealtimePokemonRepository : PokemonRepository