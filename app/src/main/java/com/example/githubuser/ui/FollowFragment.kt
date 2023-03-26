package com.example.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.data.Result
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private var username: String? = null
    private lateinit var followViewModel: FollowViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()

        followViewModel = viewModels<FollowViewModel> {
            ViewModelFactory.getInstance(requireActivity())
        }.value



        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            username?.let { followViewModel.getDataFollowers(it) }
            followViewModel.followerUser.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    observeResult(result)
                }
            }
        } else {
            username?.let { followViewModel.getDataFollowing(it) }
            followViewModel.followingUser.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    observeResult(result)
                }
            }
        }

    }

    private fun setDataList(item: List<ItemsItem>) {
        val adapter = ListUserAdapter(item)
        binding.followCard.adapter = adapter
    }

    private fun observeResult(result: Result<List<ItemsItem>>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                val data = result.data
                setDataList(data)
            }
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.followCard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.followCard.addItemDecoration(itemDecoration)
    }

    companion object {
        const val ARG_POSITION = "POSITION"
        const val ARG_USERNAME = "USERNAME"
    }
}