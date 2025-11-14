package com.example.cleanarchsample.gitUser.presentation

import app.cash.turbine.test
import com.example.cleanarchsample.BaseViewModelUnitTest
import com.example.cleanarchsample.domain.usecase.GetGitUsersUseCase
import com.example.cleanarchsample.presentation.model.GitUserViewData
import com.example.cleanarchsample.presentation.userList.GitUserState
import com.example.cleanarchsample.presentation.userList.GitUserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class GitUserViewModelTest: BaseViewModelUnitTest() {

    private val getGitUserUseCase: GetGitUsersUseCase = mockk()
    private lateinit var viewModel: GitUserViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GitUserViewModel(getGitUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getUsers is called and succeed should emit Loading then users`() = runTest {
        val mockUsers = listOf(
            GitUserViewData(
                id = "1",
                imgUrl = "url",
                login = "login",
                name = "name",
                publicRepo = 0
            )
        )
        coEvery { getGitUserUseCase() } returns mockUsers

        viewModel.state.test {
            viewModel.getUsers()

            assertEquals(GitUserState(), awaitItem())
            val successState = awaitItem()
            assertEquals(GitUserState(isLoading = false, users = mockUsers), successState)
            assertEquals(mockUsers, (successState).users)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when getUsers is called with a exception state should emit Loading then Error`() = runTest {
        val errorMessage = "Something went wrong: java.lang.Exception"
        coEvery { getGitUserUseCase() }.throws(Exception())

        viewModel.state.test {
            viewModel.getUsers()

            assertEquals(GitUserState(), awaitItem())

            val errorState = awaitItem()
            assertEquals(
                GitUserState(
                    isLoading = false,
                    users = emptyList(),
                    errorMessage = errorMessage), errorState)
            ensureAllEventsConsumed()
        }
    }
}