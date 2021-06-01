package com.example.githubusersearchapp.data.repository.searchUser

import android.util.Log
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.SearchRequest
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.UserRequest
import com.example.githubusersearchapp.data.repository.searchUser.model.Result
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UsersResponse
import java.util.function.BiFunction

class SearchRepositoryImpl(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchLocalDataSource: SearchLocalDataSource
) : SearchRepository {
    override suspend fun getUsers(request: SearchRequest): Result<UsersResponse> {
        val local = searchLocalDataSource.getAllFavoriteUsers().associateBy{ it?.login }
        val remote = searchRemoteDataSource.getUsers(request)

        val result = remote.data?.users?.map { it.copy(isFavorite = (local[it.login] != null)) }
        return remote.copy(data = remote.data!!.copy(users = result!!))

    }
    override suspend fun getUser(request: UserRequest): Result<UserResponse> {
        val local = getFavoriteUser(request.username)
        val remote = searchRemoteDataSource.getUser(request)

        return if(local != null) {
            Result(remote.data?.copy(memo = local.memo))
        } else {
            remote
        }
    }

    override suspend fun getFavoriteUser(key: String): UserResponse? {
        return searchLocalDataSource.getFavoriteUser(key)
    }

    override suspend fun getAllFavoriteUsers(): List<UserResponse> {
        val s = searchLocalDataSource.getAllFavoriteUsers()
        return if (s.isNullOrEmpty()) {
            listOf()
        } else {
            s.map { it!! }
        }
    }

    override suspend fun getFavoriteLikeUsers(query: String): List<UserResponse?> {
        return searchLocalDataSource.getFavoriteLikeUsers(query)
    }

    override suspend fun saveFavoriteUser(userResponse: UserResponse) {
        searchLocalDataSource.saveFavoriteUser(userResponse)
    }

    override suspend fun deleteFavoriteUser(userResponse: UserResponse) {
        searchLocalDataSource.deleteFavoriteUser(userResponse)
    }
}