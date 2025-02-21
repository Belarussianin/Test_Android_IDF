package com.idf.test.ui.screens.user_details

sealed interface UserDetailsEffect {
    object NavigateToUserList : UserDetailsEffect
}