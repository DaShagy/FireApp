package com.example.fireapp.data.repositories

import android.util.Log
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.util.ResultWrapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class FirestorePokemonRepositoryImpl @Inject constructor() : PokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) {
        FirebaseFirestore.getInstance()
            .collection("pokemon")
            .document(pokemon.id.toString())
            .set(pokemon)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>> = callbackFlow {
        val pokemonCollection = FirebaseFirestore.getInstance()
            .collection("pokemon")

        val subscription = pokemonCollection.addSnapshotListener { snapshot, e ->
            if (e != null){
                Log.w("Firestore", "Listen failed.", e)
                trySend(ResultWrapper.Failure(e.cause!!))
                return@addSnapshotListener
            }

            val pokemonList = mutableListOf<Pokemon>()
            for (document in snapshot!!){
                val pokemon = document.toObject(Pokemon::class.java)
                pokemonList.add(pokemon)
            }

            trySend(ResultWrapper.Success(pokemonList as List<Pokemon>))
        }

        awaitClose { subscription.remove() }
    }

    override suspend fun deletePokemon(pokemon: Pokemon) {
        FirebaseFirestore.getInstance()
            .collection("pokemon")
            .document(pokemon.id.toString())
            .delete()
    }
}
