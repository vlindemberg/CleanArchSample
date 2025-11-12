package com.example.cleanarchsample.data.repository

import com.example.cleanarchsample.data.datasource.GitRepoRemoteDataSource
import com.example.cleanarchsample.domain.extensions.toGitRepositories
import com.example.cleanarchsample.domain.model.GitRepositories
import com.example.cleanarchsample.domain.repository.GitRepoRepository
import javax.inject.Inject

class GitRepoRepositoryImpl @Inject constructor(
    private val gitRepoRemoteDataSource: GitRepoRemoteDataSource
) : GitRepoRepository {
    override suspend fun getGitRepositories(username: String): List<GitRepositories> =
        gitRepoRemoteDataSource.fetchRepositories(username).toGitRepositories()
}
