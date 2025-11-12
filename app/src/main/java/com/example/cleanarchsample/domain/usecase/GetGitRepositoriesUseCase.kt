package com.example.cleanarchsample.domain.usecase

import com.example.cleanarchsample.domain.repository.GitRepoRepository
import com.example.cleanarchsample.presentation.extensions.toGitRepositoriesViewData
import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData
import javax.inject.Inject

class GetGitRepositoriesUseCase @Inject constructor(
    private val gitRepoRepository: GitRepoRepository
) {
    suspend operator fun invoke(username: String): List<GitRepositoriesViewData> {
        return gitRepoRepository.getGitRepositories(username).toGitRepositoriesViewData()
    }
}
