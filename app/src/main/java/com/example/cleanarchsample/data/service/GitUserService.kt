package com.example.cleanarchsample.data.service

import com.example.cleanarchsample.data.model.GitUserDetailsResponse
import com.example.cleanarchsample.data.model.GitUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitUserService {

    @GET("users")
    suspend fun getGitUsers(): List<GitUserResponse>

    @GET("users/{username}")
    suspend fun getGitUserDetails(
        @Path("username") username: String
    ): GitUserDetailsResponse
}