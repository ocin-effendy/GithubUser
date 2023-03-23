package com.example.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.Result

class FollowViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {

    lateinit var followerUser : LiveData<Result<List<ItemsItem>>>

    lateinit var followingUser : LiveData<Result<List<ItemsItem>>>

    fun getDataFollowers(username: String) {
        followerUser = githubUserRepository.dataFollowers(username)
    }

    fun getDataFollowing(username: String) {
        followingUser = githubUserRepository.dataFollowing(username)
    }

}