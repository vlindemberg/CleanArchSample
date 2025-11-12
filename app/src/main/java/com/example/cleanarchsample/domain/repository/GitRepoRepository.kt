package com.example.cleanarchsample.domain.repository

import com.example.cleanarchsample.domain.model.GitRepositories

interface GitRepoRepository {
    suspend fun getGitRepositories(username: String): List<GitRepositories>
}
