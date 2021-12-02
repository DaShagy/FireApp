package com.example.fireapp.di

import com.example.fireapp.data.repositories.FirestorePokemonRepositoryImpl
import com.example.fireapp.data.repositories.RealtimePokemonRepositoryImpl
import com.example.fireapp.domain.repositories.FirestorePokemonRepository
import com.example.fireapp.domain.repositories.RealtimePokemonRepository
import com.example.fireapp.domain.usecases.*
import com.example.fireapp.presentation.viewmodels.FirestoreViewModel
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    val repositoriesModule = module {
        single<FirestorePokemonRepository> { FirestorePokemonRepositoryImpl() }
        single<RealtimePokemonRepository> { RealtimePokemonRepositoryImpl() }
    }

    val viewModelsModule = module {
        viewModel { FirestoreViewModel(get()) }
        viewModel { RealtimeViewModel(get()) }
    }

    val useCasesModule = module {
        single { FirestoreUseCases(get()) }
        single { RealtimeUseCases(get()) }
    }
}