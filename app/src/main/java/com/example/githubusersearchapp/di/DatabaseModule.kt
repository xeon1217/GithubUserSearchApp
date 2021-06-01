package com.example.githubusersearchapp.di

import androidx.room.Room
import com.example.githubusersearchapp.data.local.room.AppDatabase
import com.example.githubusersearchapp.data.local.room.SearchLocalDataSourceImpl
import com.example.githubusersearchapp.data.repository.searchUser.SearchLocalDataSource
import org.koin.dsl.module

val databaseModule = module {

    single<SearchLocalDataSource> {
        SearchLocalDataSourceImpl(get())
    }

    single {
        get<AppDatabase>().userDao()
    }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database").build()
    }
}