package com.example.githubuserssearch.ui.detail_user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserssearch.data.response.DetailUserResponse
import com.example.githubuserssearch.data.retrofit.ApiConfig
import com.example.githubuserssearch.database.Favorite
import com.example.githubuserssearch.database.FavoriteDao
import com.example.githubuserssearch.database.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(app: Application) : AndroidViewModel(app) {
    val user = MutableLiveData<DetailUserResponse>()

    private var favDao : FavoriteDao?
    private var favdb : FavoriteDatabase

    init{
        favdb = FavoriteDatabase.getDatabase(app)
        favDao = favdb?.FavoriteDao()
    }
    fun setUserDetail(login: String) {
        ApiConfig.getApiService().getDetailUser(login)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addFavorite(login : String, id : Int, avatar : String){
        CoroutineScope(Dispatchers.IO).launch {
            var fav = Favorite(login,id, avatar)
            favDao?.addToFavorite(fav)
        }
    }

    suspend fun checkFav(id : Int) = favDao?.checkFavorite(id)

    fun removeFavorite(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.removeFavorite(id)
        }
    }
}