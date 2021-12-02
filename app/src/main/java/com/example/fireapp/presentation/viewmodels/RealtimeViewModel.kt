package com.example.fireapp.presentation.viewmodels

import com.example.fireapp.domain.usecases.RealtimeUseCases

class RealtimeViewModel(
    private val useCases: RealtimeUseCases
) : BaseViewModel(useCases)