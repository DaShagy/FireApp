package com.example.fireapp.domain.usecases

import com.example.fireapp.domain.repositories.FirestorePokemonRepository

class FirestoreUseCases(repository: FirestorePokemonRepository) : BaseUseCases(repository)