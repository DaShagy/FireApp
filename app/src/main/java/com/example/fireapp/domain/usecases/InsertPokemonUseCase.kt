package com.example.fireapp.domain.usecases

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository

class InsertPokemonUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemon: Pokemon) = repository.insertPokemon(pokemon)
}