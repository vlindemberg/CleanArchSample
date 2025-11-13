package com.example.cleanarchsample.gitRepo.data

import com.example.cleanarchsample.domain.model.GitRepositories

internal val gitRepositoriesMock = listOf(
    GitRepositories(
        id = "1",
        forks = 0,
        name = "teste"
    ),
    GitRepositories(
        id = "2",
        forks = 0,
        name = "teste-2"
    )
)

internal val gitRepositoriesSuccessGsonMock = """
            [
                {"id": "1", "forks": 0, "name": "teste"},
                {"id": "2", "forks": 0, "name": "teste-2"}
            ]
        """.trimIndent()

internal val gitRepositoriesErrorGsonMock = """
            {
              "error": "Internal Server Error"
            }
        """.trimIndent()