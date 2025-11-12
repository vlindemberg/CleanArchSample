package com.example.cleanarchsample.presentation.extensions

import com.example.cleanarchsample.domain.model.GitRepositories
import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData

fun List<GitRepositories>.toGitRepositoriesViewData(): List<GitRepositoriesViewData> =
    this.map { repo ->
        GitRepositoriesViewData(
            id = repo.id,
            forks = repo.forks,
            name = repo.name
        )
    }