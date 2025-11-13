package com.example.cleanarchsample.gitUser.data

import com.example.cleanarchsample.BaseRepositoryUnitTest
import com.example.cleanarchsample.data.datasource.GitUserRemoteDataSourceImpl
import com.example.cleanarchsample.data.repository.GitUserRepositoryImpl
import com.example.cleanarchsample.data.service.GitUserService
import com.example.cleanarchsample.domain.repository.GitUserRepository
import com.example.cleanarchsample.gitRepo.data.gitRepositoriesErrorGsonMock
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
internal class GitUserRepositoryTest: BaseRepositoryUnitTest() {

    private lateinit var repository: GitUserRepository

    @Before
    fun setup() {
        repository = createRepository()
    }


    @Test
    fun `getGitUsers should return users When service return with success`() = runTest {
        //Given
        val expectedResult = gitUsersMock
        val expectedPath = "/users"
        enqueueResponse(
            code = 200,
            body = gitUsersSuccessGsonMock
        )

        //When
        val result = repository.getGitUsers()
        val request = mockWebServer.takeRequest()

        //Then
        assertEquals(result, expectedResult)
        assertEquals(expectedPath, request.path)
    }

    @Test
    fun `getGitUserDetails should return users details When service return with success`() = runTest {
        //Given
        val expectedResult = gitUserDetailsMock
        val username = "vlindemberg"
        val expectedPath = "/users/${username}"
        enqueueResponse(
            code = 200,
            body = gitUserDetailsSuccessGsonMock
        )

        //When
        val result = repository.getGitUserDetails(username)
        val request = mockWebServer.takeRequest()

        //Then
        assertEquals(result, expectedResult)
        assertEquals(expectedPath, request.path)
    }

    @Test
    fun `getGitUsers should return error When service return with error`() = runTest {
        //Given
        enqueueResponse(
            code = 500,
            body = gitUserErrorGsonMock
        )

        //When
        val exception = assertThrows(HttpException::class.java) {
            runBlocking { repository.getGitUsers() }
        }

        //Then
        assertEquals(500, exception.code())
    }

    private fun createRepository(): GitUserRepository {
        val service = retrofit.create(GitUserService::class.java)
        val dataSource = GitUserRemoteDataSourceImpl(
            gitUserService = service
        )
        return GitUserRepositoryImpl(gitUserRemoteDataSource = dataSource)
    }

}