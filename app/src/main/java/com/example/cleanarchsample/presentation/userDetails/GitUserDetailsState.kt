package com.example.cleanarchsample.presentation.userDetails

import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData
import com.example.cleanarchsample.presentation.model.GitUserViewData

data class GitUserDetailsState (
    val isLoading: Boolean = true,
    val gitRepoList: List<GitRepositoriesViewData> = emptyList(),
    val user: GitUserViewData = GitUserViewData(),
    val errorMessage: String = "",
)