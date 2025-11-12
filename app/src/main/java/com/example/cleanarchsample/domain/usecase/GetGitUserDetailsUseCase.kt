package com.example.cleanarchsample.domain.usecase

import com.example.cleanarchsample.domain.repository.GitUserRepository
import com.example.cleanarchsample.presentation.extensions.toGitUserViewData
import com.example.cleanarchsample.presentation.model.GitUserViewData
import javax.inject.Inject

class GetGitUserDetailsUseCase @Inject constructor(
    private val gitUserRepository: GitUserRepository
) {
    suspend operator fun invoke(username: String): GitUserViewData {
        return gitUserRepository.getGitUserDetails(username).toGitUserViewData()
    }
}