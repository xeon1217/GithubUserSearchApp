package com.example.githubusersearchapp.di

import com.example.githubusersearchapp.ui.detail.DetailViewModel
import com.example.githubusersearchapp.ui.favorite.FavoriteViewModel
import com.example.githubusersearchapp.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel(get(), get())
    }

    viewModel {
        DetailViewModel(get(), get())
    }
}