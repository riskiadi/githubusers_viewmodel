package com.alkalynx.githubusers.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.githubusers.R
import com.alkalynx.githubusers.adapter.FollowAdapter
import com.alkalynx.githubusers.databinding.FragmentFollowerBinding
import com.alkalynx.githubusers.view_model.DetailViewModel

class FollowerFragment : Fragment(R.layout.fragment_follower) {

    companion object {
        const val EXTRA_NAME = "username_extra"
    }

    private lateinit var binding: FragmentFollowerBinding
    private val followerAdapter: FollowAdapter = FollowAdapter()
    private var fragmentBinding: FragmentFollowerBinding? = null
    private lateinit var detailViewModel: DetailViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowerBinding.bind(view)
        fragmentBinding = binding

        binding.recyclerFollower.setHasFixedSize(true)
        binding.recyclerFollower.layoutManager = LinearLayoutManager(context)
        binding.recyclerFollower.adapter = followerAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        showLoading(true)
        detailViewModel.searchFollower(arguments?.getString(EXTRA_NAME).toString())
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
            binding.recyclerFollower.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerFollower.visibility = View.VISIBLE
        }
    }

}