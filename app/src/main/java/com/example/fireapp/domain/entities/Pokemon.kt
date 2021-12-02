package com.example.fireapp.domain.entities

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Pokemon(
    val id: Int? = null,
    val name: String? = null,
    val order: Int? = null,
    val height: Int? = null,
    val weight: Int? = null
){
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "order" to order,
            "height" to height,
            "weight" to weight
        )
    }
}
