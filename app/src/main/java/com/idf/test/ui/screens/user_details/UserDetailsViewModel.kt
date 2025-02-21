package com.idf.test.ui.screens.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.idf.test.data.repository.IUserRepository
import com.idf.test.ui.model.UserMapper
import com.idf.test.ui.navigation.UserDetails
import com.idf.test.ui.screens.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    userRepository: IUserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<UserDetailsState, UserDetailsEvent, UserDetailsEffect>() {

    private val userId = UserDetails.from(savedStateHandle).userId

    override val uiState: StateFlow<UserDetailsState> = userRepository.getUser(userId)
        .catch { emit(null) }
        .map { user ->
            if (user == null) {
                UserDetailsState.Error
            } else {
                UserDetailsState.Ready(user = UserMapper.mapToUIModel(user))
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UserDetailsState.Loading)

    override fun onEvent(event: UserDetailsEvent) {
        when (event) {
            is UserDetailsEvent.BackClick -> {
                sendEffect(UserDetailsEffect.NavigateToUserList)
            }
        }
    }
}