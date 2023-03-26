package com.example.githubuser.ui


import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser.databinding.ActivityMainBinding
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.githubuser.R
import org.junit.Test

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private lateinit var binding: ActivityMainBinding

    @Before
    fun setup(){
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity { activity ->
            binding = activity.getBinding()
        }

    }

    @Test
    fun testClickRecyclerViewItem() {
        val recyclerView = onView(withId(binding.viewCard.id))
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun testClickFavoriteUserMenu(){
        onView(withId(R.id.favorite)).perform(click())
    }

    @Test
    fun testClickSettingMenu(){
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.setting)).perform(click())
    }

}