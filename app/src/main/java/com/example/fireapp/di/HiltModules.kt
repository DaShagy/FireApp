package com.example.fireapp.di

import com.example.fireapp.data.repositories.FirestorePokemonRepositoryImpl
import com.example.fireapp.data.repositories.RealtimePokemonRepositoryImpl
import com.example.fireapp.domain.repositories.PokemonRepository
import com.example.fireapp.domain.usecases.UseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class FirestoreRepository

@Qualifier
annotation class RealtimeRepository

//Repositories will live same as the activity that requires them
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @FirestoreRepository
    abstract fun providesFirestoreRepository(impl: FirestorePokemonRepositoryImpl): PokemonRepository

    @Binds
    @RealtimeRepository
    abstract fun providesRealtimeRepository(impl: RealtimePokemonRepositoryImpl): PokemonRepository

}

@InstallIn(ViewModelComponent::class)
@Module
class UseCasesModule {

    @Provides
    @Named("FirestoreUseCases")
    fun providesFirestoreUseCases(
        @FirestoreRepository repo: PokemonRepository
    ): UseCases = UseCases(repo)

    @Provides
    @Named("RealtimeUseCases")
    fun providesRealtimeUseCases(
        @RealtimeRepository repo: PokemonRepository
    ): UseCases = UseCases(repo)
}