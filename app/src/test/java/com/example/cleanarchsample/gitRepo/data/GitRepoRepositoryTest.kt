package com.example.cleanarchsample.gitRepo.data

import com.example.cleanarchsample.BaseRepositoryUnitTest
import com.example.cleanarchsample.data.datasource.GitRepoRemoteDataSourceImpl
import com.example.cleanarchsample.data.repository.GitRepoRepositoryImpl
import com.example.cleanarchsample.data.service.GitRepoService
import com.example.cleanarchsample.domain.repository.GitRepoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
internal class GitRepoRepositoryTest: BaseRepositoryUnitTest() {

    private lateinit var repository: GitRepoRepository

    @Before
    fun setup() {
        repository = createRepository()
    }

    @Test
    fun `getGitRepositories should return repositories When service return with success`() = runTest {
        //Given
        val expectedResult = gitRepositoriesMock
        val username = "vlindemberg"
        val expectedPath = "/users/${username}/repos"
        enqueueResponse(
            code = 200,
            body = gitRepositoriesSuccessGsonMock
        )

        //When
        val result = repository.getGitRepositories(username)
        val request = mockWebServer.takeRequest()

        //Then
        assertEquals(result, expectedResult)
        assertEquals(expectedPath, request.path)

    }

    @Test
    fun `getGitRepositories should return error When service return with error`() = runTest {
        //Given
        val username = "vlindemberg"
        enqueueResponse(
            code = 500,
            body = gitRepositoriesErrorGsonMock
        )

        //When
        val exception = assertThrows(HttpException::class.java) {
            runBlocking { repository.getGitRepositories(username) }
        }

        //Then
        assertEquals(500, exception.code())
    }

    private fun createRepository(): GitRepoRepository {
        val service = retrofit.create(GitRepoService::class.java)
        val dataSource = GitRepoRemoteDataSourceImpl(
            gitRepoService = service
        )
        return GitRepoRepositoryImpl(gitRepoRemoteDataSource = dataSource)
    }
}
