package com.example.fireapp.domain.usecases

import com.example.fireapp.domain.repositories.RealmPokemonRepository

class RealmUseCases(
    repository: RealmPokemonRepository
) : BaseUseCases(repository)