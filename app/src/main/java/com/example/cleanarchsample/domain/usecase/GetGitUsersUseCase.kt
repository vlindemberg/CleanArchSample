package com.example.cleanarchsample.domain.usecase

import com.example.cleanarchsample.domain.repository.GitUserRepository
import com.example.cleanarchsample.presentation.extensions.toUsersViewData
import com.example.cleanarchsample.presentation.model.GitUserViewData
import javax.inject.Inject

class GetGitUsersUseCase @Inject constructor(
    private val gitUserRepository: GitUserRepository
) {
    suspend operator fun invoke(): List<GitUserViewData> {
        return gitUserRepository.getGitUsers().toUsersViewData()
    }
}