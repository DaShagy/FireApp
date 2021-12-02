package com.example.fireapp.presentation.viewmodels

import com.example.fireapp.domain.usecases.FirestoreUseCases

class FirestoreViewModel(
    private val useCases: FirestoreUseCases
): BaseViewModel(useCases)