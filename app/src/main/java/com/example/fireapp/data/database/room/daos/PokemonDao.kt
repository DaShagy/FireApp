package com.example.fireapp.data.database.room.daos

import androidx.room.*
import com.example.fireapp.data.database.room.entities.RoomPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM Pokemon")
    fun getAllPokemon() : Flow<List<RoomPokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: RoomPokemon)

    @Delete
    suspend fun deletePokemon(pokemon: RoomPokemon)
}