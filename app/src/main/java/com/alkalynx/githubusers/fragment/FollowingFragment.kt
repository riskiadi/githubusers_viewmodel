package com.alkalynx.githubusers.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.githubusers.R
import com.alkalynx.githubusers.adapter.FollowAdapter
import com.alkalynx.githubusers.databinding.FragmentFollowingBinding
import com.alkalynx.githubusers.view_model.DetailViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {

    companion object {
        const val EXTRA_NAME = "username_extra"
    }

    private lateinit var binding: FragmentFollowingBinding
    private val followerAdapter: FollowAdapter = FollowAdapter()
    private var fragmentBinding: FragmentFollowingBinding? = null
    private lateinit var detailViewModel: DetailViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowingBinding.bind(view)
        fragmentBinding = binding

        binding.recyclerFollowing.setHasFixedSize(true)
        binding.recyclerFollowing.layoutManager = LinearLayoutManager(context)
        binding.recyclerFollowing.adapter = followerAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        showLoading(true)
        detailViewModel.searchFollowing(arguments?.getString(EXTRA_NAME).toString())
        detailViewModel.getUser().observe(viewLifecycleOwner, {
            if (it != null) {
                followerAdapter.setData(it)
                showLoading(false)
            }
        })

    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerFollowing.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerFollowing.visibility = View.VISIBLE
        }
    }

}