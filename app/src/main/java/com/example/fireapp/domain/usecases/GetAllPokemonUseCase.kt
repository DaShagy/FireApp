package com.example.fireapp.domain.usecases

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke() : Flow<ResultWrapper<List<Pokemon>>> =
        repository.getAllPokemon()
}