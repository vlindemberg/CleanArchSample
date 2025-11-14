package com.example.cleanarchsample.presentation.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchsample.domain.usecase.GetGitRepositoriesUseCase
import com.example.cleanarchsample.domain.usecase.GetGitUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitUserDetailsViewModel @Inject constructor(
    private val getGitRepositoriesUseCase: GetGitRepositoriesUseCase,
    private val getGitUserDetailsUseCase: GetGitUserDetailsUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(GitUserDetailsState(isLoading = true))
    val states: StateFlow<GitUserDetailsState> = _states

    fun getRepositories(username: String) {
        viewModelScope.launch {
            runCatching {
                getGitRepositoriesUseCase(username)
            }.onSuccess { repositories ->
                _states.update {
                    GitUserDetailsState(
                        isLoading = false,
                        gitRepoList = repositories,
                        user = it.user
                    )
                }
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            runCatching {
                getGitUserDetailsUseCase(username)
            }.onSuccess { user ->
                _states.update {
                    GitUserDetailsState(
                        isLoading = false,
                        gitRepoList = it.gitRepoList,
                        user = user
                    )
                }
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun handleError(error: Throwable) {
        _states.update {
            GitUserDetailsState(
                isLoading = false,
                gitRepoList = it.gitRepoList,
                user = it.user,
                errorMessage = "Something went wrong: $error"
            )
        }
    }
}
