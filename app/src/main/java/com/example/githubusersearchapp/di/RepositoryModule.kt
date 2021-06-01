package com.example.githubusersearchapp.di

import com.example.githubusersearchapp.data.repository.searchUser.SearchRepository
import com.example.githubusersearchapp.data.repository.searchUser.SearchRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
}