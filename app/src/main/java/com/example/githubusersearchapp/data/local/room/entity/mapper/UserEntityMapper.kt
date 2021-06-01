package com.example.githubusersearchapp.data.local.room.entity.mapper

import com.example.githubusersearchapp.data.local.room.entity.UserEntity
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse

object UserEntityMapper {

    fun fromEntity(userEntity: UserEntity?): UserResponse? {
        return userEntity?.run {
            UserResponse(
                login, id, avatarUrl, company, blog, location, email, isFavorite, memo
            )
        }
    }

    fun fromEntityList(userEntityList: List<UserEntity>): List<UserResponse?> {
        return userEntityList.map { fromEntity(it) }
    }

    fun toEntity(userResponse: UserResponse): UserEntity {
        return userResponse.run {
            UserEntity(
                login, id, avatarUrl, company, blog, location, email, isFavorite, memo
            )
        }
    }
}