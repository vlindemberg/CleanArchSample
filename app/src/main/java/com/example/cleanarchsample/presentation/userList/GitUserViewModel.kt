package com.example.cleanarchsample.presentation.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchsample.domain.usecase.GetGitUsersUseCase
import com.example.cleanarchsample.presentation.model.GitUserViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GitUserViewModel @Inject constructor(
    private val getGitUsersUseCase: GetGitUsersUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GitUserState(isLoading = true))
    val state: StateFlow<GitUserState> = _state

    fun getUsers() {
        viewModelScope.launch {
            runCatching {
                getGitUsersUseCase()
            }.onSuccess { users ->
                _state.update {
                    GitUserState(isLoading = false, users = users)
                }
            }.onFailure { error ->
                _state.update {
                    GitUserState(
                        isLoading = false,
                        users = emptyList(),
                        errorMessage = "Something went wrong: $error"
                    )
                }
            }
        }
    }

    fun filterList (
        name: String?,
        userListLocation: List<GitUserViewData>
    ): List<GitUserViewData> {
        val filteredList = ArrayList<GitUserViewData>()
        if (name != null) {
            for (i in userListLocation) {
                if (i.name.lowercase(Locale.ROOT).contains(name)) {
                    filteredList.add(i)
                }
            }
        }
        return filteredList
    }
}