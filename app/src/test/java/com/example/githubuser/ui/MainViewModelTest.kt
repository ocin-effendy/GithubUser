package com.example.githubuser.ui

import android.util.Log
import android.view.View
import androidx.datastore.dataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.local.room.FavoriteUserDao
import com.example.githubuser.data.remote.retrofit.ApiService
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.concurrent.Executors
import com.example.githubuser.data.Result
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ResponseGithub
import org.mockito.Mockito.`when`
import kotlin.math.log

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


}


