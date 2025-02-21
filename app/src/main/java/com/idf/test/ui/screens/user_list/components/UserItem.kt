package com.idf.test.ui.screens.user_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.idf.test.R
import com.idf.test.ui.model.UserUI
import com.idf.test.ui.screens.shared.UserUIPreviewParameterProvider
import com.idf.test.ui.theme.TestIDFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    user: UserUI,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.user_id_text, user.id), fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.user_name_text, user.name), fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.user_username_text, user.username))
            Text(text = stringResource(R.string.user_email_text, user.email))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserItemPreview(
    @PreviewParameter(UserUIPreviewParameterProvider::class) user: UserUI
) {
    TestIDFTheme {
        UserItem(
            user = user
        )
    }
}