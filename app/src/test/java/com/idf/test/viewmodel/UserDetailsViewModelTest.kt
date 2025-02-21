package com.idf.test.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.idf.test.data.model.AddressDTO
import com.idf.test.data.model.CompanyDTO
import com.idf.test.data.model.GeoDTO
import com.idf.test.data.model.UserDTO
import com.idf.test.data.repository.IUserRepository
import com.idf.test.ui.navigation.UserDetails
import com.idf.test.ui.screens.user_details.UserDetailsEffect
import com.idf.test.ui.screens.user_details.UserDetailsEvent
import com.idf.test.ui.screens.user_details.UserDetailsState
import com.idf.test.ui.screens.user_details.UserDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailsViewModelTest {

    @MockK
    private lateinit var userRepository: IUserRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: UserDetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val userDTO = UserDTO(
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should set state to Ready with user on success`() = runTest {
        // Given
        val userId = 1
        val userFlow: Flow<UserDTO?> = flowOf(userDTO)
        val userDetails = UserDetails(userId)
        savedStateHandle.mockkToRoute(userDetails)
        every { userRepository.getUser(userId) } returns userFlow

        // When
        viewModel = UserDetailsViewModel(userRepository, savedStateHandle)
        testScheduler.advanceUntilIdle()

        // Then
        val stateList = mutableListOf<UserDetailsState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        assertTrue(stateList[0] is UserDetailsState.Loading)
        assertTrue(stateList[1] is UserDetailsState.Ready)
        assertEquals(userDTO.id, (stateList[1] as UserDetailsState.Ready).user.id)
        assertEquals(userDTO.name, (stateList[1] as UserDetailsState.Ready).user.name)
        job.cancel()
    }

    @Test
    fun `init should set state to Error on null user`() = runTest {
        // Given
        val userId = 1
        val userFlow: Flow<UserDTO?> = flowOf(null)
        val userDetails = UserDetails(userId)
        savedStateHandle.mockkToRoute(userDetails)
        every { userRepository.getUser(userId) } returns userFlow

        // When
        viewModel = UserDetailsViewModel(userRepository, savedStateHandle)
        testScheduler.advanceUntilIdle()

        // Then
        val stateList = mutableListOf<UserDetailsState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        assertTrue(stateList[0] is UserDetailsState.Loading)
        assertTrue(stateList[1] is UserDetailsState.Error)
        job.cancel()
    }

    @Test
    fun `init should set state to Error on exception`() = runTest {
        // Given
        val userId = 1
        val userFlow: Flow<UserDTO?> = flow { throw Exception("Network error") }
        val userDetails = UserDetails(userId)
        savedStateHandle.mockkToRoute(userDetails)
        every { userRepository.getUser(userId) } returns userFlow

        // When
        viewModel = UserDetailsViewModel(userRepository, savedStateHandle)
        testScheduler.advanceUntilIdle()

        // Then
        val stateList = mutableListOf<UserDetailsState>()
        val job = launch(testDispatcher) {
            viewModel.uiState.toList(stateList)
        }
        testScheduler.advanceUntilIdle()

        assertTrue(stateList[0] is UserDetailsState.Loading)
        assertTrue(stateList[1] is UserDetailsState.Error)
        job.cancel()
    }

    @Test
    fun `onEvent BackClick should send NavigateToUserList effect`() = runTest {
        // Given
        val userId = 1
        val userFlow: Flow<UserDTO?> = flowOf(userDTO)
        val userDetails = UserDetails(userId)
        savedStateHandle.mockkToRoute(userDetails)
        every { userRepository.getUser(userId) } returns userFlow
        viewModel = UserDetailsViewModel(userRepository, savedStateHandle)
        testScheduler.advanceUntilIdle()

        // When
        val effectList = mutableListOf<UserDetailsEffect>()
        val job = launch(testDispatcher) {
            viewModel.uiEffectFlow.toList(effectList)
        }
        viewModel.onEvent(UserDetailsEvent.BackClick)
        testScheduler.advanceUntilIdle()

        // Then
        assertEquals(1, effectList.size)
        assertTrue(effectList[0] is UserDetailsEffect.NavigateToUserList)
        job.cancel()
    }
}