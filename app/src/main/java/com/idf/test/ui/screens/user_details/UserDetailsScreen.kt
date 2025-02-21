package com.idf.test.ui.screens.user_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.idf.test.R
import com.idf.test.ui.screens.shared.SingleEventEffect
import com.idf.test.ui.screens.shared.UserDetailsStatePreviewParameterProvider
import com.idf.test.ui.screens.user_details.component.UserAddress
import com.idf.test.ui.screens.user_details.component.UserCompany
import com.idf.test.ui.screens.user_details.component.UserContacts
import com.idf.test.ui.theme.TestIDFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel = hiltViewModel<UserDetailsViewModel>(),
    navigateToUserList: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SingleEventEffect(viewModel.uiEffectFlow) { effect ->
        when (effect) {
            is UserDetailsEffect.NavigateToUserList -> {
                navigateToUserList()
            }
        }
    }

    BackHandler {
        viewModel.onEvent(UserDetailsEvent.BackClick)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.user_details_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(UserDetailsEvent.BackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { innerPadding ->
        UserDetailsContent(
            state = state,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun UserDetailsContent(
    state: UserDetailsState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RectangleShape
    ) {
        when (state) {
            is UserDetailsState.Error -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.user_details_error))
                }
            }

            is UserDetailsState.Loading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UserDetailsState.Ready -> {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    with(state.user) {
                        Text(text = stringResource(R.string.user_name_text, name), fontWeight = FontWeight.Bold)
                        Text(text = stringResource(R.string.user_username_text, username))
                        UserContacts(email, phone, website)
                        UserAddress(address)
                        UserCompany(company)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun UserDetailsScreenPreview(
    @PreviewParameter(UserDetailsStatePreviewParameterProvider::class) state: UserDetailsState
) {
    TestIDFTheme {
        Scaffold { innerPadding ->
            UserDetailsContent(
                state = state,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}