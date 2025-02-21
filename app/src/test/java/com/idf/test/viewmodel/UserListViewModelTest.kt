package com.idf.test.viewmodel

import com.idf.test.data.model.AddressDTO
import com.idf.test.data.model.CompanyDTO
import com.idf.test.data.model.GeoDTO
import com.idf.test.data.model.UserDTO
import com.idf.test.data.repository.IUserRepository
import com.idf.test.data.repository.RequestStatus
import com.idf.test.ui.screens.user_list.UserListEffect
import com.idf.test.ui.screens.user_list.UserListEvent
import com.idf.test.ui.screens.user_list.UserListState
import com.idf.test.ui.screens.user_list.UserListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    @MockK
    private lateinit var userRepository: IUserRepository

    private lateinit var viewModel: UserListViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val allUsersDTO: List<UserDTO> = buildList(10) {
        add(
            UserDTO(
                id = 1,
                name = "Leanne Graham",
                username = "Bret",
                email = "john.mckinley@examplepetstore.biz",
                address =
                AddressDTO(
                    street = "Kulas Light",
                    suite = "Apt. 556",
                    city = "Gwenborough",
                    zipcode = "9299",
                    geo = GeoDTO(
                        lat = "-37.3159",
                        lng = "81.1496"
                    ),
                ),
                phone = "1-770-736-8031 x56442",
                website = "hildegard.org",
                company = CompanyDTO(
                    name = "Romaguera-Jacobson",
                    catchPhrase = "Multi-layered client-server neural-net",
                    bs = "harness real-time e-markets"
                )
            )
        )
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init fetchUsers should set isLoading to true and then false on success and update users`() = runTest {
        // Given
        val userListFlow = MutableSharedFlow<List<UserDTO>>()
        val refreshStatusFlow: Flow<RequestStatus> = flow {
            emit(RequestStatus.LOADING)
            emit(RequestStatus.SUCCESSFUL)
        }

        every { userRepository.getUserList() } returns userListFlow
        coEvery { userRepository.refreshUsers() } returns refreshStatusFlow

        // When
        viewModel = UserListViewModel(userRepository)
        testScheduler.advanceUntilIdle()

        val stateList = mutableListOf<UserListState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        // Then
        assertTrue(stateList[0].isLoading)
        assertEquals(0, stateList[0].users.size)

        // Emit the users
        userListFlow.emit(allUsersDTO)
        testScheduler.advanceUntilIdle()

        assertFalse(stateList[1].isLoading)
        assertEquals(1, stateList[2].users.size)
        assertEquals(1, stateList[2].users[0].id)
        assertEquals("Leanne Graham", stateList[2].users[0].name)
        job.cancel()
    }

    @Test
    fun `init fetchUsers should set isLoading to true and then false on success`() = runTest {
        // Given
        val userListFlow: Flow<List<UserDTO>> = flowOf(allUsersDTO)
        val refreshStatusFlow: Flow<RequestStatus> = flow {
            emit(RequestStatus.LOADING)
            emit(RequestStatus.SUCCESSFUL)
        }

        every { userRepository.getUserList() } returns userListFlow
        coEvery { userRepository.refreshUsers() } returns refreshStatusFlow

        // When
        viewModel = UserListViewModel(userRepository)
        testScheduler.advanceUntilIdle()

        // Then
        val stateList = mutableListOf<UserListState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        assertTrue(stateList[0].isLoading)
        assertFalse(stateList[1].isLoading)
        job.cancel()
    }

    @Test
    fun `fetchUsers should set isLoading to true and then false on error`() = runTest {
        // Given
        val userListFlow: Flow<List<UserDTO>> = flowOf(emptyList())
        val refreshStatusFlow: Flow<RequestStatus> = flow {
            emit(RequestStatus.LOADING)
            emit(RequestStatus.ERROR)
        }

        every { userRepository.getUserList() } returns userListFlow
        coEvery { userRepository.refreshUsers() } returns refreshStatusFlow

        // When
        viewModel = UserListViewModel(userRepository)
        testScheduler.advanceUntilIdle()

        // Then
        val stateList = mutableListOf<UserListState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        assertTrue(stateList[0].isLoading)
        assertFalse(stateList[1].isLoading)
        assertEquals(0, stateList[1].users.size)
        job.cancel()
    }

    @Test
    fun `onEvent UserItemClick should send NavigateToUserDetails effect`() = runTest {
        // Given
        val userListFlow: Flow<List<UserDTO>> = flowOf(emptyList())
        val refreshStatusFlow: Flow<RequestStatus> = flowOf(RequestStatus.SUCCESSFUL)
        val userId = 1

        every { userRepository.getUserList() } returns userListFlow
        coEvery { userRepository.refreshUsers() } returns refreshStatusFlow
        viewModel = UserListViewModel(userRepository)
        testScheduler.advanceUntilIdle()

        // When
        val effectList = mutableListOf<UserListEffect>()
        val job = launch(testDispatcher) {
            viewModel.uiEffectFlow.toList(effectList)
        }
        viewModel.onEvent(UserListEvent.UserItemClick(userId))
        testScheduler.advanceUntilIdle()

        // Then
        assertEquals(1, effectList.size)
        assertTrue(effectList[0] is UserListEffect.NavigateToUserDetails)
        assertEquals(userId, (effectList[0] as UserListEffect.NavigateToUserDetails).userId)
        job.cancel()
    }

    @Test
    fun `onEvent PullToRefreshList should call fetchUsers`() = runTest {
        // Given
        val userListFlow: Flow<List<UserDTO>> = flowOf(emptyList())
        val refreshStatusFlow: Flow<RequestStatus> = flowOf(RequestStatus.SUCCESSFUL)

        every { userRepository.getUserList() } returns userListFlow
        coEvery { userRepository.refreshUsers() } returns refreshStatusFlow
        viewModel = UserListViewModel(userRepository)
        testScheduler.advanceUntilIdle()

        // When
        viewModel.onEvent(UserListEvent.PullToRefreshList)
        testScheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { userRepository.refreshUsers() }
    }
}