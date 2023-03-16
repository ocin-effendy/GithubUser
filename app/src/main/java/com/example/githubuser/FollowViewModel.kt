package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    private val _followerUser = MutableLiveData<List<ItemsItem>>()
    val followerUser : LiveData<List<ItemsItem>> = _followerUser

    private val _followingUser = MutableLiveData<List<ItemsItem>>()
    val followingUser : LiveData<List<ItemsItem>> = _followingUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun followerListUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object: Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _followerUser.value = items
                } else {
                    Log.e("FOLLOWERS", "Data Not Found")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FOLLOWERS", "onFailure: ${t.message}")
            }
        })
    }

    fun followingListUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object: Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _followingUser.value = items
                } else {
                    Log.e("FOLLOWERS", "Data Not Found")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FOLLOWERS", "onFailure: ${t.message}")
            }
        })
    }

}