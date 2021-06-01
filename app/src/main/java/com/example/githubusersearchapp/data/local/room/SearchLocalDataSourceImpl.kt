package com.example.githubusersearchapp.data.local.room

import com.example.githubusersearchapp.data.local.room.dao.UserDAO
import com.example.githubusersearchapp.data.local.room.entity.mapper.UserEntityMapper
import com.example.githubusersearchapp.data.repository.searchUser.SearchLocalDataSource
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse

class SearchLocalDataSourceImpl(
    private val userDAO: UserDAO
): SearchLocalDataSource {
    override suspend fun getAllFavoriteUsers(): List<UserResponse?> {
        return UserEntityMapper.fromEntityList(userDAO.selectAllUser())
    }

    override suspend fun getFavoriteLikeUsers(query: String): List<UserResponse?> {
        return UserEntityMapper.fromEntityList(userDAO.selectLikeUser(query))
    }

    override suspend fun getFavoriteUser(query: String): UserResponse? {
        return UserEntityMapper.fromEntity(userDAO.selectUser(query))
    }

    override suspend fun saveFavoriteUser(userResponse: UserResponse) {
        val userEntity = UserEntityMapper.toEntity(userResponse)
        userDAO.insertOrUpdateUser(userEntity)
    }

    override suspend fun deleteFavoriteUser(userResponse: UserResponse) {
        userDAO.deleteUser(userResponse.login)
    }
}