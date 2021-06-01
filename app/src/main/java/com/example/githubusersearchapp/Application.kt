package com.example.githubusersearchapp

import android.app.Application
import com.example.githubusersearchapp.di.databaseModule
import com.example.githubusersearchapp.di.networkModule
import com.example.githubusersearchapp.di.repositoryModule
import com.example.githubusersearchapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Github 사용자를 검색하고, 즐겨찾기를 관리하는 앱
 * - Github 사용자 검색 및 표시
 * - 즐겨찾기한 사용자를 로컬 DB에 저장
 * - 즐겨찾기한 사용자를 로컬 DB에서 검색 및 표시
 * - 즐겨찾기한 사용자에 대한 메모를 작성
 */

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@Application)
            androidFileProperties()
            modules(networkModule, repositoryModule, databaseModule, viewModelModule)
        }
    }
}