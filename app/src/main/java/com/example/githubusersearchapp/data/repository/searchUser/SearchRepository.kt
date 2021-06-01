package com.example.githubusersearchapp.data.repository.searchUser

import com.example.githubusersearchapp.data.remote.githubRestAPI.request.SearchRequest
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.UserRequest
import com.example.githubusersearchapp.data.repository.searchUser.model.Result
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UsersResponse

interface SearchRepository {
    suspend fun getUsers(request: SearchRequest): Result<UsersResponse>
    suspend fun getUser(request: UserRequest): Result<UserResponse>
    suspend fun getAllFavoriteUsers(): List<UserResponse?>
    suspend fun getFavoriteLikeUsers(query: String): List<UserResponse?>
    suspend fun getFavoriteUser(key: String): UserResponse?
    suspend fun saveFavoriteUser(userResponse: UserResponse)
    suspend fun deleteFavoriteUser(userResponse: UserResponse)
}