package com.example.fireapp.domain.repositories

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.util.ResultWrapper

interface PokemonRepository {
    suspend fun insertPokemon(pokemon: Pokemon)
    suspend fun getAllPokemon(): ResultWrapper<List<Pokemon>>
}