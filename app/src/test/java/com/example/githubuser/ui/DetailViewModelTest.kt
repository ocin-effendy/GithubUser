package com.example.githubuser.ui

import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.GithubUserRepository
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.DetailUserResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After


@Suppress("DEPRECATION")
class DetailViewModelTest {

    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var detailViewModel: DetailViewModel

    private val detailUserResponse = DetailUserResponse(
        login = "ocin-effendy",
        name = null,
        avatarUrl = "https://avatars.githubusercontent.com/u/78718700?v=4",
        followersUrl = "https://api.github.com/users/ocin-effendy/followers",
        followingUrl = "https://api.github.com/users/ocin-effendy/following{/other_user}",
        followers = 5,
        following = 4
    )

    private val username = "ocin-effendy"
    private val avatarUrl = "https://avatars.githubusercontent.com/u/78718700?v=4"
    private val favoriteUser = FavoriteUser(username = username, avatarUrl = avatarUrl)



    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    @Before
    fun before(){
        Dispatchers.setMain(newSingleThreadContext("Main"))

        githubUserRepository = mock(GithubUserRepository::class.java)
        detailViewModel = DetailViewModel(githubUserRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun getDetailDataUser() {
        val expectedResponse = Result.Success(detailUserResponse)
        `when`(githubUserRepository.dataGithubDetailUser(username)).thenReturn(MutableLiveData(expectedResponse))
        detailViewModel.getDetailDataUser(username)
        assertEquals(expectedResponse, detailViewModel.githubDetailUser.value )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSaveFavoriteUser() = runBlockingTest {
        detailViewModel.saveFavoriteUser(favoriteUser)
        delay(2000)
        verify(githubUserRepository).insert(favoriteUser)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDeleteFavoriteUser() = runBlockingTest {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)

        detailViewModel.deleteFavoriteUser(favoriteUser)

        advanceTimeBy(2000)

        verify(githubUserRepository).delete(favoriteUser)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}