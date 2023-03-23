package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ResponseGithub
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") query: String
     ): Call<ResponseGithub>

    @GET("users")
    fun getListUser(): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}