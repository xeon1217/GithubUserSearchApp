package com.example.githubusersearchapp.di

import com.example.githubusersearchapp.data.local.room.SearchLocalDataSourceImpl
import com.example.githubusersearchapp.data.remote.githubRestAPI.GithubRestAPI
import com.example.githubusersearchapp.data.remote.githubRestAPI.SearchRemoteDataSourceImpl
import com.example.githubusersearchapp.data.repository.searchUser.SearchLocalDataSource
import com.example.githubusersearchapp.data.repository.searchUser.SearchRemoteDataSource
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<SearchRemoteDataSource> {
        SearchRemoteDataSourceImpl(get())
    }

    single {
        get<Retrofit>().create(GithubRestAPI::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                })
        }.build()
    }

    single {
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .build()
            )
        }
    }
}