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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class DetailUser : AppCompatActivity() {

    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    lateinit var loading: LoadingDialog
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    var _isCheck = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading = LoadingDialog(this)
        loading.startLoading()

        val login = intent.getStringExtra(EXTRA_USER)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, login)

        viewModel.setUserDetail(login.toString())
        viewModel.getUserDetail().observe(this, Observer{
            if (it != null) {
                binding.userName.text = it.login
                binding.name.text = it.name
                binding.followers.text = "${it.followers} followers"
                binding.following.text = "${it.following} following"
                Glide.with(this@DetailUser).load(it.avatarUrl).centerCrop().into(binding.imageView)
                loading.isDismiss()
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            var count = viewModel.checkFav(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toogleFavorite.isChecked = true
                        _isCheck = true
                    } else {
                        binding.toogleFavorite.isChecked = false
                        _isCheck = false
                    }
                }
            }
        }

        binding.toogleFavorite.setOnClickListener {
            _isCheck = !_isCheck
            if(_isCheck){
                if (login != null && avatar != null) {
                    viewModel.addFavorite(login,id,avatar)
                }
            } else {
                viewModel.removeFavorite(id)
            }
            binding.toogleFavorite.isChecked = _isCheck
        }

        val sectionAdapter = SectionAdapter(this, bundle)
        binding.viewPager.adapter = sectionAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }


}