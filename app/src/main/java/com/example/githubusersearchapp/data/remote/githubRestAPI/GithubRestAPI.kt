package com.example.githubusersearchapp.data.remote.githubRestAPI

import com.example.githubusersearchapp.data.remote.githubRestAPI.response.GithubUserResponse
import com.example.githubusersearchapp.data.remote.githubRestAPI.response.GithubUsersResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubRestAPI {
    @GET("/search/users")
    suspend fun getUsers(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<GithubUsersResponse>

    @GET("/users/{username}")
    suspend fun getUser(
        @Path("username") userName: String
    ): Response<GithubUserResponse>
}