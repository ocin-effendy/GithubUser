package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _githubUser = MutableLiveData<ResponseGithub>()
    val githubUser : LiveData<ResponseGithub> = _githubUser



    private val _githubListUser = MutableLiveData<List<ItemsItem>>()
    val githubListUser : LiveData<List<ItemsItem>> = _githubListUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        dataListUser()
    }

    private fun dataListUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _githubListUser.value = items
                } else {
                    Log.e(TAG, "Data Not Found")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun dataGithubUser(user: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(user)
        client.enqueue(object: Callback<ResponseGithub>{
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val item = response.body()
                    _githubUser.value = item
                } else {
                    Log.i(TAG, "Data Not Found")
                }
            }
            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }




}