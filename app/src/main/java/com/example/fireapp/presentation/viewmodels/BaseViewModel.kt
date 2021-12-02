package com.example.fireapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.usecases.BaseUseCases
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val useCases: BaseUseCases) : ViewModel() {

    fun insertPokemon(pokemon: Pokemon) = viewModelScope.launch(Dispatchers.IO) {
        useCases.insertPokemonUseCase(pokemon)
    }

    val pokemonList = liveData(Dispatchers.IO){
        try {
            useCases.getAllPokemonUseCase().collect {
                emit(it)
            }
        } catch(e: Exception){
            emit(ResultWrapper.Failure(e.cause?:Throwable("Unidentified exception")))
        }
    }
}