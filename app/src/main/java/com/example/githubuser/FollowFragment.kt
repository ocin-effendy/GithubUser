package com.example.githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            username?.let { followViewModel.followerListUser(it) }
            followViewModel.followerUser.observe(viewLifecycleOwner){ item ->
                setDataList(item)
            }
        } else {
            username?.let { followViewModel.followingListUser(it) }
            followViewModel.followingUser.observe(viewLifecycleOwner){ item ->
                setDataList(item)
            }
        }

        followViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }

    private fun setDataList(item: List<ItemsItem>){
        val adapter = ListUserAdapter(item)
        binding.followCard.adapter = adapter
    }



    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.followCard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.followCard.addItemDecoration(itemDecoration)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val ARG_POSITION = "POSITION"
        const val ARG_USERNAME = "USERNAME"
    }
}