package com.idf.test.ui.screens.user_list

import androidx.annotation.StringRes

sealed interface UserListEffect {
    data class NavigateToUserDetails(val userId: Int) : UserListEffect
    data class ShowToast(@StringRes val message: Int) : UserListEffect
}