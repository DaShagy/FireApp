package com.example.fireapp.data.repositories

import android.util.Log
import com.example.fireapp.data.database.realm.MyRealmDatabase
import com.example.fireapp.data.database.realm.entities.RealmPokemon
import com.example.fireapp.data.mappers.RealmPokemonMapperRepository
import com.example.fireapp.data.mappers.RoomPokemonMapperRepository
import com.example.fireapp.domain.entities.Pokemon
import com.example.fireapp.domain.repositories.RealmPokemonRepository
import com.example.fireapp.util.ResultWrapper
import io.realm.Realm
import kotlinx.coroutines.flow.*

class RealmPokemonRepositoryImpl(
    private val mapper: RealmPokemonMapperRepository,
    private val database: MyRealmDatabase
) : RealmPokemonRepository {
    override suspend fun insertPokemon(pokemon: Pokemon) =
        database.insertRealmPokemon(mapper.transform(pokemon))

    override suspend fun getAllPokemon(): Flow<ResultWrapper<List<Pokemon>>> =
        flow {
            database.getAllRealmPokemon().collect { result ->
                result.map { mapper.transformToRepository(it) }
                    .also {
                        emit(ResultWrapper.Success(it))
                    }
            }
        }

    override suspend fun deletePokemon(pokemon: Pokemon) =
        database.deleteRealmPokemon(mapper.transform(pokemon))
}