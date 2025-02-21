package com.idf.test.ui.screens.user_list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.idf.test.R
import com.idf.test.ui.screens.shared.SingleEventEffect
import com.idf.test.ui.screens.shared.UserListStatePreviewParameterProvider
import com.idf.test.ui.screens.user_list.components.UserItem
import com.idf.test.ui.theme.TestIDFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel<UserListViewModel>(),
    navigateToUserDetails: (userId: Int) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var toast by remember { mutableStateOf<Toast?>(null) }

    SingleEventEffect(viewModel.uiEffectFlow) { effect ->
        when (effect) {
            is UserListEffect.NavigateToUserDetails -> {
                navigateToUserDetails(effect.userId)
            }

            is UserListEffect.ShowToast -> {
                toast?.cancel()
                toast = Toast.makeText(context, effect.message, Toast.LENGTH_SHORT)
                toast?.show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.user_list_title)) }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        UserListContent(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListContent(
    state: UserListState,
    onEvent: (UserListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { onEvent(UserListEvent.PullToRefreshList) },
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(state.users, key = { user -> user.id }) { user ->
                UserItem(
                    user = user,
                    modifier = Modifier.clickable { onEvent(UserListEvent.UserItemClick(user.id)) },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UserListScreenPreview(
    @PreviewParameter(UserListStatePreviewParameterProvider::class) state: UserListState
) {
    TestIDFTheme {
        Scaffold { innerPadding ->
            UserListContent(
                state = state,
                onEvent = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}