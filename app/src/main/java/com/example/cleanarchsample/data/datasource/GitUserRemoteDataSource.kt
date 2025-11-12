package com.example.cleanarchsample.data.datasource

import com.example.cleanarchsample.data.model.GitUserDetailsResponse
import com.example.cleanarchsample.data.model.GitUserResponse

interface GitUserRemoteDataSource {
    suspend fun fetchGitUsers(): List<GitUserResponse>
    suspend fun fetchGitUserDetails(username: String): GitUserDetailsResponse
}