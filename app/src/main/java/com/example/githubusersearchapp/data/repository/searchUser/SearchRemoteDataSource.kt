package com.example.githubusersearchapp.data.repository.searchUser

import com.example.githubusersearchapp.data.remote.githubRestAPI.request.SearchRequest
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.UserRequest
import com.example.githubusersearchapp.data.repository.searchUser.model.Result
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UsersResponse

interface SearchRemoteDataSource {
    suspend fun getUsers(searchRequest: SearchRequest): Result<UsersResponse>
    suspend fun getUser(usersRequest: UserRequest): Result<UserResponse>
}