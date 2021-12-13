package com.example.fireapp.data.database.realm.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmPokemon(
    @PrimaryKey
    var id: Int = -1,
    var name: String = "",
    var order: Int = -1,
    var height: Int = -1,
    var weight: Int = -1
) : RealmObject()
