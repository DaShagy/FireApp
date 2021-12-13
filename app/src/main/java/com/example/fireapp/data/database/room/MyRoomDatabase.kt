package com.example.fireapp.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fireapp.data.database.room.daos.PokemonDao
import com.example.fireapp.data.database.room.entities.RoomPokemon

@Database(
    entities = [RoomPokemon::class],
    version = 1
)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract val pokemonDao: PokemonDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: com.example.fireapp.data.database.room.MyRoomDatabase? = null

        fun getDatabase(context: Context): com.example.fireapp.data.database.room.MyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}