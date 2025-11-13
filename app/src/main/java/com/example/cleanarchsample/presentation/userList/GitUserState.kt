package com.example.cleanarchsample.presentation.userList

import com.example.cleanarchsample.presentation.model.GitUserViewData

data class GitUserState(
    val isLoading: Boolean = true,
    val users: List<GitUserViewData> = emptyList(),
    val errorMessage: String = "",
)