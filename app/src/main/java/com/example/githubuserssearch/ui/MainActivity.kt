package com.example.githubuserssearch.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserssearch.R
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.data.response.ItemsItem
import com.example.githubuserssearch.data_store.SettingPreference
import com.example.githubuserssearch.data_store.ViewModelFactory
import com.example.githubuserssearch.data_store.dataStore
import com.example.githubuserssearch.databinding.ActivityMainBinding
import com.example.githubuserssearch.ui.FavoriteUser.FavoriteActivity
import com.example.githubuserssearch.ui.ListUser.ListUserAdapter
import com.example.githubuserssearch.ui.ListUserSearch.ListUserSearchAdapter
import com.example.githubuserssearch.ui.detail_user.DetailUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var adapter2: ListUserSearchAdapter
    private var isNightMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter2 = ListUserSearchAdapter()
        binding.listUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.listUser.adapter = adapter

        val pref = SettingPreference.getInstance(application.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java)
        showListUser()

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_light_mode_24)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_dark_mode_24)
            }
        }

        NightLightMode()
        binding.topAppBar.setOnMenuItemClickListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.menu1 -> {
                    Intent(this, FavoriteActivity::class.java).also {startActivity(it) }
                    true
                }
                R.id.menu2 -> {
                    if (isNightMode){
                        viewModel.saveThemeSetting(false)
                        isNightMode =  false
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_dark_mode_24)
                        true
                    } else {
                        viewModel.saveThemeSetting(true)
                        isNightMode = true
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_light_mode_24)
                        true
                    }
                }
                else -> false
            }
        }

        binding.filteredSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()){
                    binding.listUser.adapter = adapter2
                    showListUserSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()){
                    binding.listUser.adapter = adapter2
                    showListUserSearch(newText)
                }
                return true
            }

        })
    }

    private fun showListUser(){
        showLoading(true)
        viewModel.setListUser()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickListener{
            override fun onItemClick(data : GithubResponseItem) {
                val intent = Intent(this@MainActivity, DetailUser::class.java)
                intent.putExtra(DetailUser.EXTRA_USER,data.login )
                intent.putExtra(DetailUser.EXTRA_ID, data.id)
                intent.putExtra(DetailUser.EXTRA_AVATAR,data.avatarUrl)
                startActivity(intent)
            }
        })

        viewModel.getListUser().observe(this, Observer {
            if (it != null){
                adapter.setListUser(it)
                showLoading(false)
            }
        })
    }

    private fun showListUserSearch(query : String){
        showLoading(true)
        viewModel.setListUserSearch(query)
        adapter2.setOnItemClickCallback(object : ListUserSearchAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUser::class.java)
                intent.putExtra(DetailUser.EXTRA_USER,data.login )
                intent.putExtra(DetailUser.EXTRA_ID, data.id)
                intent.putExtra(DetailUser.EXTRA_AVATAR,data.avatarUrl)
                startActivity(intent)
            }
        })

        viewModel.getListUserSearch().observe(this, Observer {
            if (it != null){
                adapter2.setListUser(it.items)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state : Boolean){
        if (state)
            binding.progressBar2.visibility = View.VISIBLE
        else
            binding.progressBar2.visibility = View.GONE
    }

    private fun NightLightMode(){
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNightMode = false
            binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_dark_mode_24)
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            isNightMode = true
            binding.topAppBar.menu.findItem(R.id.menu2).icon = getDrawable(R.drawable.baseline_light_mode_24)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}