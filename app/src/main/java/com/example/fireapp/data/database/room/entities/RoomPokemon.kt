package com.example.fireapp.data.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemon")
data class RoomPokemon(
    @PrimaryKey
    val id: Int,
    val name: String,
    val order: Int,
    val height: Int,
    val weight: Int
)
