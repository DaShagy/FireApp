package com.example.fireapp.di

import com.example.fireapp.data.repositories.FirestorePokemonRepository
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.domain.usecases.GetAllPokemonUseCase
import com.example.fireapp.domain.usecases.InsertPokemonUseCase
import com.example.fireapp.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {
    val repositoriesModule = module {
        single<PokemonRepository> { FirestorePokemonRepository() }
    }

    val viewModelsModule = module {
        viewModel { MainViewModel(get(), get()) }
    }

    val useCasesModule = module {
        single { GetAllPokemonUseCase(get()) }
        single { InsertPokemonUseCase(get()) }
    }
}