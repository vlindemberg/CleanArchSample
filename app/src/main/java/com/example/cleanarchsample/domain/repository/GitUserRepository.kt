package com.example.cleanarchsample.domain.repository

import com.example.cleanarchsample.domain.model.GitUser

interface GitUserRepository {
    suspend fun getGitUsers(): List<GitUser>
    suspend fun getGitUserDetails(username: String): GitUser
}