package com.example.githubuserssearch.data.retrofit

import com.example.githubuserssearch.data.response.DetailUserResponse
import com.example.githubuserssearch.data.response.GithubResponse
import com.example.githubuserssearch.data.response.GithubResponseItem
import com.example.githubuserssearch.data.response.GithubUserSearchResponse
import com.example.githubuserssearch.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun getGithubUser(): Call<List<GithubResponseItem>>

    @GET("search/users")
    fun getUserSearch(
        @Query("q") username: String
    ): Call<GithubUserSearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path ("username") login : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path ("username") login : String
    ): Call<List<GithubResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path ("username") login : String
    ): Call<List<GithubResponseItem>>

    @GET("users/{username}/subscriptions")
    fun getUserSubcriptions(
        @Path ("username") login : String
    ): Call<List<GithubResponseItem>>
}