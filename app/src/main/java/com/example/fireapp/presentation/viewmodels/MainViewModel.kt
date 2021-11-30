package com.example.fireapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fireapp.data.repositories.FirestorePokemonRepository
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.usecases.GetAllPokemonUseCase
import com.example.fireapp.domain.usecases.InsertPokemonUseCase
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: FirestorePokemonRepository
) : ViewModel() {

    val getAllPokemonUseCase = GetAllPokemonUseCase(repository)
    val insertPokemonUseCase = InsertPokemonUseCase(repository)

    private var _pokemonList = MutableLiveData<ResultWrapper<List<Pokemon>>>()
    val pokemonList: LiveData<ResultWrapper<List<Pokemon>>> get() = _pokemonList

    fun getAllPokemon() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            _pokemonList.postValue(getAllPokemonUseCase())
        }
    }

    fun insertPokemon(pokemon: Pokemon) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            insertPokemonUseCase(pokemon)
        }
    }
}