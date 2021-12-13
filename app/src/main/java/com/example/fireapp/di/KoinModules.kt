package com.example.fireapp.di

import android.app.Application
import androidx.room.Room
import com.example.fireapp.data.database.realm.MyRealmDatabase
import com.example.fireapp.data.database.room.MyRoomDatabase
import com.example.fireapp.data.database.room.daos.PokemonDao
import com.example.fireapp.data.mappers.RealmPokemonMapperRepository
import com.example.fireapp.data.mappers.RoomPokemonMapperRepository
import com.example.fireapp.data.repositories.FirestorePokemonRepositoryImpl
import com.example.fireapp.data.repositories.RealmPokemonRepositoryImpl
import com.example.fireapp.data.repositories.RealtimePokemonRepositoryImpl
import com.example.fireapp.data.repositories.RoomPokemonRepositoryImpl
import com.example.fireapp.domain.repositories.FirestorePokemonRepository
import com.example.fireapp.domain.repositories.RealmPokemonRepository
import com.example.fireapp.domain.repositories.RealtimePokemonRepository
import com.example.fireapp.domain.repositories.RoomPokemonRepository
import com.example.fireapp.domain.usecases.*
import com.example.fireapp.presentation.viewmodels.FirestoreViewModel
import com.example.fireapp.presentation.viewmodels.RealmViewModel
import com.example.fireapp.presentation.viewmodels.RealtimeViewModel
import com.example.fireapp.presentation.viewmodels.RoomViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModules {

    val databaseModule = module {
        fun provideDataBase(application: Application): MyRoomDatabase {
            return Room.databaseBuilder(application, MyRoomDatabase::class.java, "APP_DATABASE")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun providePokemonDao(dataBase: MyRoomDatabase): PokemonDao = dataBase.pokemonDao

        single { provideDataBase(androidApplication()) }
        single { providePokemonDao(get()) }
        single { MyRealmDatabase() }
    }

    val repositoriesModule = module {
        single<FirestorePokemonRepository> { FirestorePokemonRepositoryImpl() }
        single<RealtimePokemonRepository> { RealtimePokemonRepositoryImpl() }
        single<RoomPokemonRepository> { RoomPokemonRepositoryImpl(get(), get()) }
        single<RealmPokemonRepository> { RealmPokemonRepositoryImpl(get(), get()) }
    }

    val mappersModule = module {
        single { RoomPokemonMapperRepository() }
        single { RealmPokemonMapperRepository() }
    }

    val viewModelsModule = module {
        viewModel { FirestoreViewModel(get()) }
        viewModel { RealtimeViewModel(get()) }
        viewModel { RoomViewModel(get()) }
        viewModel { RealmViewModel(get()) }
    }

    val useCasesModule = module {
        single { FirestoreUseCases(get()) }
        single { RealtimeUseCases(get()) }
        single { RoomUseCases(get()) }
        single { RealmUseCases(get()) }
    }
}