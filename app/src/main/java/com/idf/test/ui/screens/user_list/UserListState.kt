package com.idf.test.ui.screens.user_list

import com.idf.test.ui.model.UserUI

data class UserListState(
    val users: List<UserUI> = emptyList(),
    val isLoading: Boolean = false
)