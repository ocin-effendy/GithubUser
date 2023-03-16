package com.example.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(USER)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.dataGithubDetailUser(name!!)

        detailViewModel.githubDetailUser.observe(this){ item ->
            setDataDetailUser(item)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
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

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val USER = "USER"
        @StringRes
        private val TAB_POSITION = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}