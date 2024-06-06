package com.example.githubuserssearch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    fun addToFavorite(favorite : Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavorite() : LiveData<List<Favorite>>

    @Query("SELECT COUNT(*) FROM favorite WHERE favorite.id = :id")
    fun checkFavorite(id : Int) : Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFavorite(id : Int) : Int
}