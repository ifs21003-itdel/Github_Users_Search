package com.example.githubuserssearch.ui.detail_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserssearch.data.response.DetailUserResponse
import com.example.githubuserssearch.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val user = MutableLiveData<DetailUserResponse>()

    fun setUserDetail(login : String){
        ApiConfig.getApiService().getDetailUser(login).enqueue(object :Callback <DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getUserDetail() : LiveData<DetailUserResponse>{
        return user
    }
}