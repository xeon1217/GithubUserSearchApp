package com.example.githubusersearchapp.data.repository.searchUser

import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse

interface SearchLocalDataSource {
    suspend fun getAllFavoriteUsers(): List<UserResponse?>
    suspend fun getFavoriteLikeUsers(query: String): List<UserResponse?>
    suspend fun getFavoriteUser(query: String): UserResponse?
    suspend fun saveFavoriteUser(userResponse: UserResponse)
    suspend fun deleteFavoriteUser(userResponse: UserResponse)
}