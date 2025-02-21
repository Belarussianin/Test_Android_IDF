package com.idf.test.ui.screens.user_list

sealed interface UserListEvent {
    object PullToRefreshList : UserListEvent
    data class UserItemClick(val userId: Int) : UserListEvent
}