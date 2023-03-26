package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteUsersBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUsersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()

        val mainViewModel by viewModels<MainViewModel>{
            ViewModelFactory.getInstance(application)
        }

        mainViewModel.getAllFavoriteUser().observe(this){ result ->
            val users = arrayListOf<ItemsItem>()
            result.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl.toString())
                users.add(item)
            }
            setData(users)
        }
    }

    private fun setData(users: List<ItemsItem>){
        val adapter = ListUserAdapter(users)
        binding.viewCard.adapter = adapter
    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.viewCard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.viewCard.addItemDecoration(itemDecoration)
    }

}