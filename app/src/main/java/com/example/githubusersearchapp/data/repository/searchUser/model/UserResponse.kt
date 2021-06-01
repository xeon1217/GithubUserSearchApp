package com.example.githubusersearchapp.data.repository.searchUser.model

data class UserResponse(
    val login: String,
    val id: Int,
    val avatarUrl: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    var isFavorite: Boolean,
    val memo: String,
)