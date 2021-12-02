package com.example.fireapp.domain.usecases

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCases(private val repository: PokemonRepository) {
    suspend fun getAllPokemonUseCase() : Flow<ResultWrapper<List<Pokemon>>> =
        repository.getAllPokemon()

    suspend fun insertPokemonUseCase(pokemon: Pokemon) = repository.insertPokemon(pokemon)
}