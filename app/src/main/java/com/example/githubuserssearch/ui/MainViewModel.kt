package com.example.githubuserssearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.data.response.GithubUserSearchResponse
import com.example.githubuserssearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel :ViewModel() {
    val listUser = MutableLiveData<List<GithubResponseItem>>()
    val listUserSearch = MutableLiveData<GithubUserSearchResponse>()

    fun setListUser(){
        ApiConfig.getApiService().getGithubUser().enqueue(object : Callback<List<GithubResponseItem>>{
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                if (response.isSuccessful){
                    listUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun setListUserSearch(query : String){
        ApiConfig.getApiService().getUserSearch(query).enqueue(object : Callback<GithubUserSearchResponse>{
            override fun onResponse(
                call: Call<GithubUserSearchResponse>,
                response: Response<GithubUserSearchResponse>
            ) {
                if (response.isSuccessful){
                    listUserSearch.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<GithubUserSearchResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListUser() : LiveData<List<GithubResponseItem>>{
        return listUser
    }

    fun getListUserSearch() : LiveData<GithubUserSearchResponse>{
        return listUserSearch
    }
}