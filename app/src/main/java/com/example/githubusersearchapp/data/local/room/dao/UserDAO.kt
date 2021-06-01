package com.example.githubusersearchapp.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubusersearchapp.data.local.room.entity.UserEntity

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(userEntity: UserEntity)

    @Query("DELETE FROM favoriteUsers WHERE login = :login")
    suspend fun deleteUser(login: String)

    @Query("SELECT * FROM favoriteUsers")
    suspend fun selectAllUser(): List<UserEntity>

    @Query("SELECT * FROM favoriteUsers WHERE login LIKE '%' || :login || '%'")
    suspend fun selectLikeUser(login: String): List<UserEntity>

    @Query("SELECT * FROM favoriteUsers WHERE login = :login")
    suspend fun selectUser(login: String): UserEntity
}