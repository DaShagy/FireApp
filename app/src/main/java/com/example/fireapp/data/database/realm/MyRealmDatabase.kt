package com.example.fireapp.data.database.realm

import android.util.Log
import com.example.fireapp.data.database.realm.entities.RealmPokemon
import io.realm.ObjectChangeSet
import io.realm.Realm
import io.realm.RealmObjectChangeListener
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyRealmDatabase() {
    fun getAllRealmPokemon() : Flow<RealmResults<RealmPokemon>> =
        flow {
            Realm.getDefaultInstance().use { realmInstance ->
                emit (realmInstance.where(RealmPokemon::class.java).findAll())
            }
        }


    fun insertRealmPokemon(pokemon: RealmPokemon){

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(pokemon)
            }
        }
    }

    fun deleteRealmPokemon(pokemon: RealmPokemon){
        Realm.getDefaultInstance().use {
            val pokemonList = it.where(RealmPokemon::class.java).equalTo(
                "id", pokemon.id
            ).findFirst()
            it.executeTransaction {
                pokemonList!!.deleteFromRealm()
            }
        }
    }
}