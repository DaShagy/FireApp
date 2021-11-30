package com.example.fireapp

import android.app.Application
import com.example.fireapp.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleApp)

            modules(listOf(
                KoinModules.repositoriesModule,
                KoinModules.useCasesModule,
                KoinModules.viewModelsModule
            ))
        }
    }
}