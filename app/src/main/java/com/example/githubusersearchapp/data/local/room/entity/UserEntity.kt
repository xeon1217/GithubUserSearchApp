package com.example.githubusersearchapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteUsers")
data class UserEntity(
    @PrimaryKey val login: String,
    val id: Int,
    val avatarUrl: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val isFavorite: Boolean,
    val memo: String
)