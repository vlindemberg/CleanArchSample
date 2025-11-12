package com.example.cleanarchsample.data.datasource

import com.example.cleanarchsample.data.model.GitRepositoriesResponse
import com.example.cleanarchsample.data.service.GitRepoService
import javax.inject.Inject

class GitRepoRemoteDataSourceImpl @Inject constructor(
    private val gitRepoService: GitRepoService
) : GitRepoRemoteDataSource {
    override suspend fun fetchRepositories(username: String): List<GitRepositoriesResponse> =
        gitRepoService.getGitRepositories(username)
}