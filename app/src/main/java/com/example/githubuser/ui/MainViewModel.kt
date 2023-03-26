package com.example.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ResponseGithub
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.Result

class MainViewModel(private val githubUserRepository: GithubUserRepository) : ViewModel() {

    lateinit var githubUser : LiveData<Result<ResponseGithub>>

    var githubListUser : LiveData<Result<List<ItemsItem>>> = githubUserRepository.dataListUser()

    fun getSearchDataUser(username: String) {
        githubUser = githubUserRepository.dataGithubUser(username)
    }

    fun getAllFavoriteUser() = githubUserRepository.getAllFavoriteUser()
}