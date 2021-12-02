package com.example.fireapp.data.repositories

import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.RealtimePokemonRepository
import com.example.fireapp.util.ResultWrapper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealtimePokemonRepositoryImpl : RealtimePokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) {
            val database = Firebase.database.reference
            database.child("pokemon").child(pokemon.id.toString()).setValue(pokemon)
        }


    override suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>> = callbackFlow {

        val ref = Firebase.database.getReference("pokemon")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val pokemonList: MutableList<Pokemon> = mutableListOf()
                for (pokemonSnapshot in dataSnapshot.children) {
                    val pokemon = pokemonSnapshot.getValue<Pokemon>()
                    pokemonList.add(pokemon!!)
                }
                trySendBlocking(ResultWrapper.Success(pokemonList))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                trySendBlocking(ResultWrapper.Failure(databaseError.toException()))
            }
        }

        ref.addValueEventListener(valueEventListener)

        awaitClose {
            ref.removeEventListener(valueEventListener)
        }
    }
}