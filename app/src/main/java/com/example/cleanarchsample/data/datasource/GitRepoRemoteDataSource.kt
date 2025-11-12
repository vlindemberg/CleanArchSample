package com.example.cleanarchsample.data.datasource

import com.example.cleanarchsample.data.model.GitRepositoriesResponse

interface GitRepoRemoteDataSource {
    suspend fun fetchRepositories(username: String): List<GitRepositoriesResponse>
}