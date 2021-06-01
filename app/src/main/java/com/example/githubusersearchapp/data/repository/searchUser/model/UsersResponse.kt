package com.example.githubusersearchapp.data.repository.searchUser.model

data class UsersResponse(
    val totalCount: Int,
    val users: List<UserResponse>,
)