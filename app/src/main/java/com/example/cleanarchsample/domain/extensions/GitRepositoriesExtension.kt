package com.example.cleanarchsample.domain.extensions

import com.example.cleanarchsample.data.model.GitRepositoriesResponse
import com.example.cleanarchsample.domain.model.GitRepositories

fun List<GitRepositoriesResponse>.toGitRepositories(): List<GitRepositories> =
    this.map { repo ->
        GitRepositories(
            id = repo.id.toString(),
            forks = repo.forks,
            name = repo.name,
        )
    }