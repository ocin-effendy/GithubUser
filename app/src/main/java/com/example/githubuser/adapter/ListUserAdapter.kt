package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.ui.DetailUserActivity
import de.hdodenhof.circleimageview.CircleImageView

class ListUserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoUser: CircleImageView = itemView.findViewById(R.id.photo_user)
        val nameUser: TextView = itemView.findViewById(R.id.name_user)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val userLogin = listUser[position]


        Glide.with(viewHolder.itemView.context)
            .load(userLogin.avatarUrl)
            .into(viewHolder.photoUser)
        viewHolder.nameUser.text = userLogin.login

        viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.USER, userLogin.login)
            intentDetail.putExtra(DetailUserActivity.AVATARURL, userLogin.avatarUrl)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

}