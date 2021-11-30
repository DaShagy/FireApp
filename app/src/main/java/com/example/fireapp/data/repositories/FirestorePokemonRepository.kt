package com.example.fireapp.data.repositories

import android.util.Log
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.util.ResultWrapper
import com.example.fireapp.util.ResultWrapper.Success
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestorePokemonRepository : PokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) {
        FirebaseFirestore.getInstance()
            .collection("pokemon")
            .document(pokemon.id.toString())
            .set(pokemon)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreRepository", "DocumentSnapshot written with ID: ${pokemon.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error adding document", e)
            }
    }

    override suspend fun getAllPokemon(): ResultWrapper<List<Pokemon>> {
        val eventList = mutableListOf<Pokemon>()
        val resultList = FirebaseFirestore.getInstance()
            .collection("pokemon")
            .get().await()

        for (document in resultList) {
            val name = document.getString("name")
            val id = document.getLong("id")
            val order = document.getLong("order")
            val height = document.getLong("height")
            val weight = document.getLong("weight")
            eventList.add(
                Pokemon(
                    id?.toInt(),
                    name,
                    order?.toInt(),
                    height?.toInt(),
                    weight?.toInt(),
                )
            )
        }

        return Success(eventList as List<Pokemon>)
    }
}