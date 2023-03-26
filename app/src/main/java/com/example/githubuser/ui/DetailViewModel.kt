package com.example.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.DetailUserResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {
    lateinit var githubDetailUser: LiveData<Result<DetailUserResponse>>

    fun getDetailDataUser(username: String) {
        githubDetailUser = githubUserRepository.dataGithubDetailUser(username)
    }

    fun saveFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            githubUserRepository.insert(favoriteUser)
        }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            githubUserRepository.delete(favoriteUser)
        }
    }

    fun getFavoriteUser(favoriteUser: FavoriteUser, callback: (Boolean) -> Unit) {
        githubUserRepository.search(favoriteUser) { isFavorite ->
            callback(isFavorite)
        }
    }
}