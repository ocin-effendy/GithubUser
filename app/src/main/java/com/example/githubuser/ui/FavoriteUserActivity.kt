package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteUsersBinding
import com.example.githubuser.databinding.ActivityMainBinding
import kotlin.math.log
import kotlin.reflect.typeOf

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
            Log.i("jancok", "masuk ke get all favorite")
            val users = arrayListOf<ItemsItem>()
            result.map {
                Log.i("jancok", "ini avaatar -> " + it.avatarUrl.toString())
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl.toString())
                users.add(item)
            }
            setData(users)
        }
//        mainViewModel.getAllFavoriteUser().observe(this){ result ->
//            Log.i("jancok", "masuk ke get all favorite")
//            val users = arrayListOf<ItemsItem>()
//            result.map {
//                val item = it.avatarUrl?.let { it1 -> ItemsItem(login = it.username, avatarUrl = it1) }
//                if (item != null) {
//                    users.add(item)
//                }
//            }
//            setData(users)
//        }

    }

    fun setData(users: List<ItemsItem>){
        for(i in users){
            Log.i("jancok", i.toString())
        }
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