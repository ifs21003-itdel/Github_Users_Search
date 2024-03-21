package com.example.githubuserssearch.ui.detail_user.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserssearch.R
import com.example.githubuserssearch.databinding.FragmentFollBinding
import com.example.githubuserssearch.ui.ListUser.ListUserAdapter
import com.example.githubuserssearch.ui.detail_user.DetailUser

class FragmentFollower:Fragment(R.layout.fragment_foll) {

    private var _binding : FragmentFollBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var login : String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login = arguments?.getString(DetailUser.EXTRA_USER).toString()

        _binding = FragmentFollBinding.bind(view)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.listFollower.setHasFixedSize(true)
        binding.listFollower.layoutManager = LinearLayoutManager(activity)
        binding.listFollower.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)
        showLoading(true)
        viewModel.setFollower(login)
        viewModel.getFollower().observe(viewLifecycleOwner, Observer {
            if (it != null){
                adapter.setListUser(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state : Boolean){
        if (state)
            binding.progressBar3.visibility = View.VISIBLE
        else
            binding.progressBar3.visibility = View.GONE
    }
}