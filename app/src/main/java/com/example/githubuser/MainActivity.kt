package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        showRecyclerList()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        mainViewModel.githubListUser.observe(this){ item ->
            setDataList(item)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setData(item: ResponseGithub){
        val adapter = ListUserAdapter(item.items)
        binding.viewCard.adapter = adapter
    }

    private fun setDataList(item: List<ItemsItem>){
        Log.i("niko", item.toString())
        val adapter = ListUserAdapter(item)
        binding.viewCard.adapter = adapter
    }


    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.viewCard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.viewCard.addItemDecoration(itemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        fun searchUser(username: String){
            mainViewModel.dataGithubUser(username)
            mainViewModel.githubUser.observe(this@MainActivity){ item ->
                setData(item)
            }
        }
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchUser(newText)
                return false
            }
        })
        return true
    }




}