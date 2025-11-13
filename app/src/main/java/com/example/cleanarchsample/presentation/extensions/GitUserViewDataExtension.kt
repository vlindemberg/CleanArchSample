package com.example.cleanarchsample.presentation.extensions

import com.example.cleanarchsample.domain.model.GitUser
import com.example.cleanarchsample.presentation.model.GitUserViewData

fun GitUser.toGitUserViewData(): GitUserViewData =
    GitUserViewData(
        id = this.id,
        imgUrl = this.imgUrl,
        name = this.name,
        login = this.login,
        publicRepo = this.publicRepo
    )

fun List<GitUser>.toUsersViewData() = this.map { user ->
    GitUserViewData(
        id = user.id,
        imgUrl = user.imgUrl,
        name = user.login,
    )
}