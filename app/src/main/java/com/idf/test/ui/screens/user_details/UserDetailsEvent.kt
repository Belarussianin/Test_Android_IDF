package com.idf.test.ui.screens.user_details

sealed interface UserDetailsEvent {
    object BackClick : UserDetailsEvent
}