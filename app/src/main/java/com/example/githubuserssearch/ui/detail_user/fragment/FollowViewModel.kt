package com.example.githubuserssearch.ui.detail_user.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    val follower = MutableLiveData<List<GithubResponseItem>>()
    val following = MutableLiveData<List<GithubResponseItem>>()

    fun setFollower(login : String){
        ApiConfig.getApiService().getUserFollower(login).enqueue(object : Callback<List<GithubResponseItem>>{
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                if (response.isSuccessful){
                    follower.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getFollower() : LiveData<List<GithubResponseItem>>{
        return follower
    }

    fun setFollowing(login: String){
        ApiConfig.getApiService().getUserFollowing(login).enqueue(object : Callback<List<GithubResponseItem>>{
            override fun onResponse(
                call: Call<List<GithubResponseItem>>,
                response: Response<List<GithubResponseItem>>
            ) {
                if (response.isSuccessful){
                    following.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<List<GithubResponseItem>>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getFollowing() : LiveData<List<GithubResponseItem>>{
        return following
    }
}