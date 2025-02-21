package com.idf.test.ui.navigation

import androidx.annotation.Keep
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.idf.test.ui.screens.user_list.UserListScreen
import com.idf.test.ui.screens.user_details.UserDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
@Keep
object UserList

@Serializable
@Keep
data class UserDetails(val userId: Int) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle): UserDetails {
            return savedStateHandle.toRoute<UserDetails>()
        }
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = UserList,
        modifier = modifier
    ) {
        userListDestination(
            onNavigateToUserDetails = { userId ->
                navController.navigate(
                    route = UserDetails(userId = userId)
                )
            }
        )

        userDetailsDestination(
            onNavigateToUserList = {
                navController.navigateUp()
            }
        )
    }
}

fun NavGraphBuilder.userListDestination(
    onNavigateToUserDetails: (userId: Int) -> Unit
) {
    composable<UserList>(
        enterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
    ) {
        UserListScreen(navigateToUserDetails = onNavigateToUserDetails)
    }
}

fun NavGraphBuilder.userDetailsDestination(
    onNavigateToUserList: () -> Unit
) {
    composable<UserDetails>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        UserDetailsScreen(navigateToUserList = onNavigateToUserList)
    }
}