package com.seattle.gituserfinder.network_service

import com.seattle.gituserfinder.model.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

public interface RestApiService {

    @GET("/users/{username}")
    fun findUser(@Path("username") username: String): Call<UserInfo>

    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserInfo>>
}