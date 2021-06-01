package com.example.githubusersearchapp.data.remote.githubRestAPI.response.mapper

import android.util.Log
import com.example.githubusersearchapp.data.remote.githubRestAPI.response.GithubUserResponse
import com.example.githubusersearchapp.data.remote.githubRestAPI.response.GithubUsersResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UsersResponse

object GithubUsersResponseEntityMapper {
    fun fromEntity(remoteResponse: GithubUsersResponse): UsersResponse {
        return remoteResponse.run {
            UsersResponse(
                totalCount = totalCount,
                users = items.map { item ->
                    UserResponse(
                        login = item.login,
                        id = item.id,
                        avatarUrl = item.avatarUrl,
                        company = "",
                        blog = "",
                        location = "",
                        email = "",
                        isFavorite = false,
                        memo = ""
                    )
                }
            )
        }
    }

    fun fromEntity(remoteResponse: GithubUserResponse): UserResponse {
        Log.e("ddd", "$remoteResponse")
        return remoteResponse.run {
            UserResponse(
                login = login,
                id = id,
                avatarUrl = if (avatarUrl.isNullOrEmpty()) null else avatarUrl,
                company = if (company.isNullOrEmpty()) null else company,
                blog = if (blog.isNullOrEmpty()) null else blog,
                location = if (location.isNullOrEmpty()) null else location,
                email = if (email.isNullOrEmpty()) null else email,
                isFavorite = false,
                memo = ""
            )
        }
    }
}