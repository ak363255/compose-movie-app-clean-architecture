package com.example.movieapp.dimodule.networkmodule

import com.example.data.netowrk.MovieApi
import com.example.data.netowrk.NetworkService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }
    single {
        NetworkService(get())
    }
}