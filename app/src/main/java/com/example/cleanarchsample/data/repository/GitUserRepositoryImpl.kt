package com.example.cleanarchsample.data.repository

import com.example.cleanarchsample.data.datasource.GitUserRemoteDataSource
import com.example.cleanarchsample.domain.extensions.toUser
import com.example.cleanarchsample.domain.extensions.toUserDetails
import com.example.cleanarchsample.domain.model.GitUser
import com.example.cleanarchsample.domain.repository.GitUserRepository
import javax.inject.Inject

class GitUserRepositoryImpl @Inject constructor(
    private val gitUserRemoteDataSource: GitUserRemoteDataSource
) : GitUserRepository {
    override suspend fun getGitUsers(): List<GitUser> =
        gitUserRemoteDataSource.fetchGitUsers().toUser()

    override suspend fun getGitUserDetails(username: String): GitUser =
        gitUserRemoteDataSource.fetchGitUserDetails(username).toUserDetails()
}