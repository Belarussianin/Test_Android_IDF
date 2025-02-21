package com.idf.test.ui.screens.user_details

import com.idf.test.ui.model.UserUI

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    data class Ready(val user: UserUI) : UserDetailsState()
    object Error : UserDetailsState()
}