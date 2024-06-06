package com.example.githubuserssearch.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite")
data class Favorite(
    val login : String,
    @PrimaryKey
    val id : Int,
    val avatar_url : String
) : Serializable
