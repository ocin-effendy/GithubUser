package com.example.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val USER = "USER"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(USER)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.dataGithubDetailUser(name!!)

        detailViewModel.githubDetailUser.observe(this){ item ->
            setDataDetailUser(item)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}