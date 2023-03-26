package com.example.githubuser.ui


import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.GithubUserRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import com.example.githubuser.data.Result
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.ResponseGithub
import org.mockito.Mockito.`when`

class MainViewModelTest {

    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var mainViewModel: MainViewModel

    private val userResponse = ResponseGithub(
        totalCount = 1,
        incompleteResults = false,
        items = listOf(
            ItemsItem(
                login = "ocin-effendy",
                avatarUrl = "https://avatars.githubusercontent.com/u/78718700?v=4"
            )
        )
    )

    @Before
    fun before(){
        githubUserRepository = mock(GithubUserRepository::class.java)
        mainViewModel = MainViewModel(githubUserRepository)
    }

    @Test
    fun getSearchDataUser(){
        val username = "ocin-effendy"
        val expectedResponse = Result.Success(userResponse)
        `when`(githubUserRepository.dataGithubUser(username)).thenReturn(MutableLiveData(expectedResponse))
        mainViewModel.getSearchDataUser(username)
        assertEquals(expectedResponse, mainViewModel.githubUser.value)

    }

}


