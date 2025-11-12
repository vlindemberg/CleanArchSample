package com.example.cleanarchsample.data.datasource

import com.example.cleanarchsample.data.model.GitUserDetailsResponse
import com.example.cleanarchsample.data.model.GitUserResponse
import com.example.cleanarchsample.data.service.GitUserService
import javax.inject.Inject

class GitUserRemoteDataSourceImpl @Inject constructor(
    private val gitUserService: GitUserService
) : GitUserRemoteDataSource {
    override suspend fun fetchGitUsers(): List<GitUserResponse> =
        gitUserService.getGitUsers()

    override suspend fun fetchGitUserDetails(username: String): GitUserDetailsResponse =
        gitUserService.getGitUserDetails(username)
}