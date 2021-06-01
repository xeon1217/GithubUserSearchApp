package com.example.githubusersearchapp.data.repository.searchUser.model

data class UserView(
    val data: UserResponse,
    val viewType: Int = ViewType.NORMAL.ordinal
)

enum class ViewType() {
    NORMAL, DETAIL
}