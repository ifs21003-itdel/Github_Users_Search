package com.example.githubuserssearch.ui.detail_user

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserssearch.R
import com.example.githubuserssearch.databinding.ActivityDetailUserBinding
import com.example.githubuserssearch.ui.detail_user.fragment.SectionAdapter
import com.example.githubuserssearch.util.LoadingDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.log

class DetailUser : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    lateinit var loading: LoadingDialog
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading = LoadingDialog(this)
        loading.startLoading()

        val login = intent.getStringExtra(EXTRA_USER)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, login)

        viewModel.setUserDetail(login.toString())
        viewModel.getUserDetail().observe(this, Observer{
            if (it != null) {
                binding.userName.text = it.login
                binding.linkGithub.text = it.htmlUrl
                binding.followers.text = "${it.followers} followers"
                binding.following.text = "${it.following} following"
                Glide.with(this@DetailUser).load(it.avatarUrl).centerCrop().into(binding.imageView)
                loading.isDismiss()
            }
        })

        val sectionAdapter = SectionAdapter(this, bundle)
        binding.viewPager.adapter = sectionAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
}