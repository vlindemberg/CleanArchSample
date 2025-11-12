package com.example.cleanarchsample.data.service

import com.example.cleanarchsample.data.model.GitRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitRepoService {

    @GET("users/{username}/repos")
    suspend fun getGitRepositories(
        @Path("username") name: String
    ) : List<GitRepositoriesResponse>
}