package com.idf.test.ui.screens.user_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.idf.test.R

@Composable
fun UserContacts(
    email: String,
    phone: String,
    website: String,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.user_email_text, email))
        Text(text = stringResource(R.string.user_phone_text, phone))
        Text(text = stringResource(R.string.user_website_text, website))
    }
}