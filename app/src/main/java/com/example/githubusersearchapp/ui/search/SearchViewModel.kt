package com.example.githubusersearchapp.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.SearchRequest
import com.example.githubusersearchapp.data.repository.searchUser.SearchRepository
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.data.repository.searchUser.model.UserView
import com.example.githubusersearchapp.extension.addAll
import com.example.githubusersearchapp.extension.clear
import com.example.githubusersearchapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val searchRepository: SearchRepository
) : BaseViewModel(application) {

    private val _users = MutableLiveData<MutableList<UserView>>()
    val users: LiveData<List<UserView>> = Transformations.map(_users) { it?.toList() }

    lateinit var request: SearchRequest
    var isLastPage: Boolean = false

    fun searchUsers(query: String) {
        if (query.isNotEmpty()) {
            request = SearchRequest(
                q = "$query in:login",
                page = 1
            )
            isLastPage = false
            _users.clear()
            request()
        } else {
            showMessage(R.string.search_prompt_empty_str)
        }
    }

    fun request() {
        uiScope.launch {
            searchRepository.getUsers(
                request
            ).apply {
                data?.apply {
                    if (totalCount == 0) {
                        showMessage(R.string.search_result_not_found_str)
                    }
                    isLastPage = (totalCount - (request.page * request.perPage) <= 0)
                    _users.addAll(users.map { UserView(it) })
                }
                exception?.apply {
                    Log.e("SearchUserViewModel", "$exception")
                }
            }
        }
    }

    fun loadUsers() {
        if (!isLastPage) {
            request = request.copy(page = request.page + 1)
            request()
        } else {
            showMessage(R.string.search_result_empty_str)
        }
    }

    fun reload() {
        _users.clear()
        request()
    }

    fun favorite(user: UserResponse) {
        uiScope.launch {
            searchRepository.run {
                if (user.isFavorite) {
                    saveFavoriteUser(user)
                } else {
                    deleteFavoriteUser(user)
                }
            }
        }
    }
}