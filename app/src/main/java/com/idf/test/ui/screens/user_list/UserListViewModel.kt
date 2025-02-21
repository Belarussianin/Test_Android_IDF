package com.idf.test.ui.screens.user_list

import androidx.lifecycle.viewModelScope
import com.idf.test.R
import com.idf.test.data.repository.IUserRepository
import com.idf.test.data.repository.RequestStatus
import com.idf.test.ui.model.UserMapper
import com.idf.test.ui.screens.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : BaseViewModel<UserListState, UserListEvent, UserListEffect>() {

    private val _userList = userRepository.getUserList()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    private val _isLoading = MutableStateFlow(true)

    override val uiState: StateFlow<UserListState> = combine(
        _userList,
        _isLoading
    ) { userList, isLoading ->
        UserListState(
            users = userList.map(UserMapper::mapToUIModel),
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserListState(_userList.value.map(UserMapper::mapToUIModel), _isLoading.value)
    )

    private var fetchJob: Job? = null

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            userRepository.refreshUsers().collect { refreshStatus ->
                when (refreshStatus) {
                    RequestStatus.LOADING -> {
                        _isLoading.update { true }
                    }

                    RequestStatus.SUCCESSFUL -> {
                        _isLoading.update { false }
                    }

                    RequestStatus.ERROR -> {
                        _isLoading.update { false }
                        sendEffect(UserListEffect.ShowToast(R.string.network_refresh_failed_toast_message))
                    }
                }
            }
        }
    }

    override fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.UserItemClick -> {
                sendEffect(UserListEffect.NavigateToUserDetails(event.userId))
            }

            is UserListEvent.PullToRefreshList -> {
                fetchUsers()
            }
        }
    }
}