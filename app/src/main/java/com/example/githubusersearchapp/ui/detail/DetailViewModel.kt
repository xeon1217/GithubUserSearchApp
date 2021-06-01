package com.example.githubusersearchapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubusersearchapp.R
import com.example.githubusersearchapp.data.remote.githubRestAPI.request.UserRequest
import com.example.githubusersearchapp.data.repository.searchUser.SearchRepository
import com.example.githubusersearchapp.data.repository.searchUser.model.UserResponse
import com.example.githubusersearchapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application,
    private val searchRepository: SearchRepository
) : BaseViewModel(application) {
    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    fun getUser(username: String) {
        uiScope.launch {
            searchRepository.getUser(
                request = UserRequest(username = username)
            ).apply {
                data?.apply{
                    _user.value = this
                }
                exception?.apply{
                    Log.e("DetailUserViewModel", "$exception")
                }
            }
        }
    }

    fun saveUser(user: UserResponse?) {
        uiScope.launch {
            if (user != null) {
                searchRepository.saveFavoriteUser(user)
                showMessage(R.string.success_save_str)
            }
        }
    }
}