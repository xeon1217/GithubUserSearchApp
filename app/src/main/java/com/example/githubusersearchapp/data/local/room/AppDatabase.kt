package com.example.githubusersearchapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubusersearchapp.data.local.room.dao.UserDAO
import com.example.githubusersearchapp.data.local.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}