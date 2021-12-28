package com.example.fireapp.presentation.viewmodels

import androidx.lifecycle.*
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.domain.usecases.UseCases
import com.example.fireapp.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    @Named("FirestoreUseCases") private val useCases: UseCases
)  : ViewModel(){

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