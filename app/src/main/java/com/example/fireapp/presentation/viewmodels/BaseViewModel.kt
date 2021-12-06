package com.example.fireapp.presentation.viewmodels

import androidx.lifecycle.*
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.usecases.BaseUseCases
import com.example.fireapp.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val useCases: BaseUseCases) : ViewModel() {

    private var _cardPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    val cardPokemon: LiveData<Pokemon> get() = _cardPokemon

    val pokemonList = liveData(Dispatchers.IO){
        try {
            useCases.getAllPokemonUseCase().collect {
                emit(it)
            }
        } catch(e: Exception){
            emit(ResultWrapper.Failure(e.cause?:Throwable("Unidentified exception")))
        }
    }

    fun setCardPokemon(pokemon: Pokemon){
        _cardPokemon.value = pokemon
    }

    fun insertPokemon(pokemon: Pokemon) = viewModelScope.launch(Dispatchers.IO) {
        useCases.insertPokemonUseCase(pokemon)
    }

    fun deletePokemon(pokemon: Pokemon) = viewModelScope.launch(Dispatchers.IO) {
        useCases.deletePokemonUseCase(pokemon)
    }

}