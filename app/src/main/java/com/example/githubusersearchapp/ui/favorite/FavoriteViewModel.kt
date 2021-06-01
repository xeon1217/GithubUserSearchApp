package com.example.githubusersearchapp.ui.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubusersearchapp.data.repository.searchUser.SearchRepository
import com.example.githubusersearchapp.data.repository.searchUser.model.UserView
import com.example.githubusersearchapp.data.repository.searchUser.model.ViewType
import com.example.githubusersearchapp.extension.addAll
import com.example.githubusersearchapp.extension.clear
import com.example.githubusersearchapp.extension.removeItem
import com.example.githubusersearchapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(
    application: Application,
    private val searchRepository: SearchRepository
) : BaseViewModel(application) {

    private val _users = MutableLiveData<MutableList<UserView>>()
    val users: LiveData<List<UserView>> = Transformations.map(_users) { it?.toList() }

    fun getFavoriteList() {
        _users.clear()

        uiScope.launch {
            _users.addAll(searchRepository.getAllFavoriteUsers().map {
                UserView(data = it!!, viewType = ViewType.DETAIL.ordinal)
            }.sortedBy { it.data.login.toLowerCase() })
        }
    }

    fun search(query: String) {
        _users.clear()

        uiScope.launch {
            _users.addAll(searchRepository.getFavoriteLikeUsers(query).map {
                UserView(data = it!!, viewType = ViewType.DETAIL.ordinal)
            }.sortedBy { it.data.login.toLowerCase() })
        }
    }

    fun favorite(user: UserView) {
        uiScope.launch {
            searchRepository.run {
                if (user.data.isFavorite) {
                    saveFavoriteUser(user.data)
                } else {
                    deleteFavoriteUser(user.data)
                    _users.removeItem(user)
                }
            }
        }
    }
}