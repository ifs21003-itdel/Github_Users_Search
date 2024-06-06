package com.example.githubuserssearch.ui.FavoriteUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserssearch.database.Favorite
import com.example.githubuserssearch.databinding.ActivityFavoriteBinding
import com.example.githubuserssearch.ui.detail_user.DetailUser

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.listFavorite.setHasFixedSize(true)
        binding.listFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.listFavorite.adapter = adapter
        getUserFavorite()
    }

    private fun getUserFavorite(){
        binding.progressBar4.visibility = View.VISIBLE
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickListener{
            override fun onItemClick(data : Favorite) {
                val intent = Intent(this@FavoriteActivity, DetailUser::class.java)
                intent.putExtra(DetailUser.EXTRA_USER,data.login )
                intent.putExtra(DetailUser.EXTRA_ID, data.id)
                intent.putExtra(DetailUser.EXTRA_AVATAR,data.avatar_url)
                startActivity(intent)
            }
        })

        viewModel.getFavorite()?.observe(this, Observer {
            if (it != null){
                adapter.setListFavorite(it)
                binding.progressBar4.visibility = View.GONE
            }
        })
    }
}