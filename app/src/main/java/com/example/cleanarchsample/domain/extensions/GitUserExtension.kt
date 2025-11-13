package com.example.cleanarchsample.domain.extensions

import com.example.cleanarchsample.data.model.GitUserDetailsResponse
import com.example.cleanarchsample.data.model.GitUserResponse
import com.example.cleanarchsample.domain.model.GitUser

fun List<GitUserResponse>.toUser(): List<GitUser> =
    this.map { user ->
        GitUser(
            id = user.id.toString(),
            imgUrl = user.avatarUrl,
            login = user.login,
            publicRepo = 0
        )
    }

fun GitUserDetailsResponse.toUserDetails(): GitUser =
    GitUser(
        id = this.id.toString(),
        imgUrl = this.avatarUrl,
        name = this.login,
        login = this.login,
        publicRepo = this.publicRepos
    )