package com.example.movieapp

import android.app.Application
import android.content.Context
import com.example.movieapp.dimodule.networkmodule.networkModule
import com.example.movieapp.dimodule.usecasemodule.useCaseModule
import com.example.movieapp.dimodule.viewmodelmodule.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    companion object{
        private lateinit var movieApplication: MovieApplication
        val applicationContext : Context get() = movieApplication.applicationContext
    }
    override fun onCreate() {
        super.onCreate()
        movieApplication = this
        startKoin {
            androidContext(this@MovieApplication)
            modules(networkModule, useCaseModule, viewmodelModule)
        }
    }


}