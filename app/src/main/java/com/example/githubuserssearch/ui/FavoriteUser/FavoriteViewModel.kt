package com.example.githubuserssearch.ui.FavoriteUser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserssearch.database.Favorite
import com.example.githubuserssearch.database.FavoriteDao
import com.example.githubuserssearch.database.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(app : Application) : AndroidViewModel(app){
    val listFavorite = MutableLiveData<List<Favorite>>()

    private var favDao : FavoriteDao?
    private var favdb : FavoriteDatabase

    init{
        favdb = FavoriteDatabase.getDatabase(app)
        favDao = favdb?.FavoriteDao()
    }

    fun getFavorite() : LiveData<List<Favorite>>? {
        return favDao?.getFavorite()
    }
}