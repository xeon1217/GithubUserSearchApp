package com.example.githubusersearchapp.data.remote.githubRestAPI

import android.util.Log
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.SearchRequest
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.UserRequest
import com.example.githubusersearchapp.data.remote.githubRestAPI.response.mapper.GithubUsersResponseEntityMapper
import com.example.githubusersearchapp.data.repository.searchUser.SearchRemoteDataSource
import com.example.githubusersearchapp.data.repository.searchUser.model.Result
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UsersResponse
import kotlin.Exception

class SearchRemoteDataSourceImpl(private val githubSearchApi: GithubRestAPI) :
    SearchRemoteDataSource {
    override suspend fun getUsers(searchRequest: SearchRequest): Result<UsersResponse> {
        val response = searchRequest.run {
            githubSearchApi.getUsers(
                q = q,
                sort = sort,
                order = order,
                perPage = perPage,
                page = page
            )
        }
        Log.e("datasource", "${response.code()}, ${response.raw()}")
        return try {
            Result(data = GithubUsersResponseEntityMapper.fromEntity(response.body()!!))
        } catch (e: Exception) {
            Log.e("datasource", "${response.errorBody()}, ${response.message()}")
            return Result(exception = e)
        }
    }

    override suspend fun getUser(usersRequest: UserRequest): Result<UserResponse> {
        val response = usersRequest.run {
            githubSearchApi.getUser(userName = username)
        }
        return try {
            Result(data = GithubUsersResponseEntityMapper.fromEntity(response.body()!!))
        } catch (e: Exception) {
            Log.e("datasource", "${response.raw()}, ${response.message()}")
            return Result(exception = e)
        }
    }
}