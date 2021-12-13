package com.example.fireapp

import android.app.Application
import com.example.fireapp.di.KoinModules
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        startKoin {
            androidContext(this@SampleApp)

            modules(listOf(
                KoinModules.repositoriesModule,
                KoinModules.useCasesModule,
                KoinModules.viewModelsModule,
                KoinModules.databaseModule,
                KoinModules.mappersModule
            ))
        }
    }
}