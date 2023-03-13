package com.example.githubuser

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
}