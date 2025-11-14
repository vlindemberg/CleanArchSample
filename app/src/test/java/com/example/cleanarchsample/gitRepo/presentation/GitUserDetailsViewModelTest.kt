package com.example.cleanarchsample.gitRepo.presentation

import app.cash.turbine.test
import com.example.cleanarchsample.BaseViewModelUnitTest
import com.example.cleanarchsample.domain.usecase.GetGitRepositoriesUseCase
import com.example.cleanarchsample.domain.usecase.GetGitUserDetailsUseCase
import com.example.cleanarchsample.presentation.model.GitRepositoriesViewData
import com.example.cleanarchsample.presentation.model.GitUserViewData
import com.example.cleanarchsample.presentation.userDetails.GitUserDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GitUserDetailsViewModelTest: BaseViewModelUnitTest() {

    private val getGitUserDetailsUseCase: GetGitUserDetailsUseCase = mockk()
    private val getGitRepositoriesUseCase: GetGitRepositoriesUseCase = mockk()
    private lateinit var viewModel: GitUserDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GitUserDetailsViewModel(getGitRepositoriesUseCase, getGitUserDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepositories should update state with repo list on success`() = runTest {
        val username = "vlindemberg"
        val fakeRepos = listOf(GitRepositoriesViewData(id = "1", forks = 0, name = "name"))

        coEvery { getGitRepositoriesUseCase(username) } returns fakeRepos

        viewModel.states.test {
            viewModel.getRepositories(username)

            // initial state
            val initialState = awaitItem()
            assert(initialState.isLoading)

            // final state
            val finalState = awaitItem()
            assert(!finalState.isLoading)
            assert(finalState.gitRepoList == fakeRepos)
            assert(finalState.errorMessage.isEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getRepositories should update state with error on failure`() = runTest {
        val username = "vlindemberg"
        val exception = RuntimeException("API error")

        coEvery { getGitRepositoriesUseCase(username) } throws exception

        viewModel.states.test {
            viewModel.getRepositories(username)

            // initial state
            val initialState = awaitItem()
            assert(initialState.isLoading)

            // final state
            val finalState = awaitItem()
            assert(!finalState.isLoading)
            assert(finalState.errorMessage.contains("Something went wrong"))
            assert(finalState.gitRepoList.isEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getUserDetails should update state with user on success`() = runTest {
        val username = "vlindemberg"
        val fakeUser = GitUserViewData(
            id = "1",
            imgUrl = "url",
            login = "login",
            name = "name",
            publicRepo = 0
        )
        coEvery { getGitUserDetailsUseCase(username) } returns fakeUser

        viewModel.states.test {
            viewModel.getUserDetails(username)

            // initial state
            val initialState = awaitItem()
            assert(initialState.isLoading)

            // final state
            val finalState = awaitItem()
            assert(!finalState.isLoading)
            assert(finalState.user == fakeUser)
            assert(finalState.errorMessage.isEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getUserDetails should update state with error on failure`() = runTest {
        val username = "vlindemberg"
        val exception = IllegalStateException("Network down")

        coEvery { getGitUserDetailsUseCase(username) } throws exception

        viewModel.states.test {
            viewModel.getUserDetails(username)

            // initial state
            val initialState = awaitItem()
            assert(initialState.isLoading)

            // final state
            val finalState = awaitItem()
            assert(!finalState.isLoading)
            assert(finalState.errorMessage.contains("Something went wrong"))
            assert(finalState.user == GitUserViewData())

            cancelAndConsumeRemainingEvents()
        }
    }
}