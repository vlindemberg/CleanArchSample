package com.example.cleanarchsample.gitUser.data

import com.example.cleanarchsample.domain.model.GitUser

internal val gitUsersMock = listOf(
    GitUser(
        id = "1",
        imgUrl = "img.png",
        login = "teste.teste",
        publicRepo = 0
    )
)

internal val gitUserDetailsMock = GitUser(
        id = "1",
        imgUrl = "img.png",
        name = "teste",
        login = "teste.teste",
        publicRepo = 0
    )

internal val gitUsersSuccessGsonMock = """
            [
                {"id": "1", "avatar_url": "img.png", "login": "teste.teste", "publicRepo": 0}
            ]
        """.trimIndent()

internal val gitUserDetailsSuccessGsonMock = """
                {"id": "1", "avatar_url": "img.png", "name": "teste", "login": "teste.teste", "publicRepo": 0}
        """.trimIndent()

internal val gitUserErrorGsonMock = """
            {
              "error": "Internal Server Error"
            }
        """.trimIndent()