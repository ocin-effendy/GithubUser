package com.example.githubuser.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.githubuser.data.Result
import com.example.githubuser.data.local.entity.FavoriteUser
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var fab: FloatingActionButton
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(USER)
        val avatarUrl = intent.getStringExtra(AVATARURL)

        detailViewModel = viewModels<DetailViewModel> {
            ViewModelFactory.getInstance(application)
        }.value

        detailViewModel.getDetailDataUser(name!!)

        detailViewModel.githubDetailUser.observe(this){ result ->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        setDataDetailUser(data)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = name
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_POSITION[position])
        }.attach()
        supportActionBar?.elevation = 0f

        fab = binding.fab

        val dataFavorite = FavoriteUser(
            name,
            avatarUrl
        )
        detailViewModel.getFavoriteUser(dataFavorite) { isFavorite ->
            runOnUiThread {
                setFavoriteButtonImage(isFavorite)
            }
        }

        fab.setOnClickListener {
            detailViewModel.getFavoriteUser(dataFavorite) { isFavorite ->
                runOnUiThread {
                    setFavoriteButtonImage(!isFavorite)
                    if (isFavorite) {
                        detailViewModel.deleteFavoriteUser(dataFavorite)
                        Toast.makeText(this, "remove $name from favorite", Toast.LENGTH_SHORT).show()
                    } else {
                        detailViewModel.saveFavoriteUser(dataFavorite)
                        Toast.makeText(this, "Add $name to favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setFavoriteButtonImage(isFavorite: Boolean) {
        val drawable = if (isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        fab.setImageDrawable(ContextCompat.getDrawable(fab.context, drawable))
    }

    @SuppressLint("SetTextI18n")
    private fun setDataDetailUser(item: DetailUserResponse){
        Glide.with(this)
            .load(item.avatarUrl)
            .into(binding.imageProfile)
        binding.nameUser.text = item.name
        binding.loginUser.text = item.login
        binding.followersUser.text = "${item.followers} Followers"
        binding.followingUser.text = "${item.following} Following"
    }

    override fun onResume() {
        super.onResume()
        val name = intent.getStringExtra(USER)
        val avatarUrl = intent.getStringExtra(AVATARURL)
        val dataFavorite = FavoriteUser(
            name!!,
            avatarUrl
        )
        detailViewModel.getFavoriteUser(dataFavorite) { isFavorite ->
            runOnUiThread {
                setFavoriteButtonImage(isFavorite)
            }
        }
    }

    companion object {
        const val USER = "USER"
        const val AVATARURL ="AVATARURL"
        @StringRes
        private val TAB_POSITION = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}