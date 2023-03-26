package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.local.room.FavoriteUserRoomDatabase
import com.example.githubuser.data.remote.retrofit.ApiConfig
import java.util.concurrent.Executors

object Injection {

    fun provideRepository(context: Context): GithubUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserRoomDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val executorService = Executors.newSingleThreadExecutor()
        return GithubUserRepository.getInstance(apiService, dao, executorService)

    }

}