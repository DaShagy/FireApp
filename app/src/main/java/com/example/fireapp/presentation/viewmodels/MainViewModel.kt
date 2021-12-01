package com.example.fireapp.presentation.viewmodels

import androidx.lifecycle.*
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.usecases.GetAllPokemonUseCase
import com.example.fireapp.domain.usecases.InsertPokemonUseCase
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    val getAllPokemonUseCase : GetAllPokemonUseCase,
    val insertPokemonUseCase : InsertPokemonUseCase
) : ViewModel() {

    val pokemonList = liveData(Dispatchers.IO){
        try {
            getAllPokemonUseCase().collect {
                emit(it)
            }
        } catch(e: Exception){
            emit(ResultWrapper.Failure(e.cause?:Throwable("Unidentified exception")))
        }
    }

    fun insertPokemon(pokemon: Pokemon) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            insertPokemonUseCase(pokemon)
        }
    }
}