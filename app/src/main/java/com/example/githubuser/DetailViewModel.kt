package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _githubDetailUser = MutableLiveData<DetailUserResponse>()
    val githubDetailUser : LiveData<DetailUserResponse> = _githubDetailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun dataGithubDetailUser(user: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val item = response.body()
                    _githubDetailUser.value = item
                } else {
                    Log.i(TAG, "Data Not Found")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}