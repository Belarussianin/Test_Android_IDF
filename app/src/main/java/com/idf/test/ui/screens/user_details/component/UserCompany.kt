package com.idf.test.ui.screens.user_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.idf.test.R
import com.idf.test.ui.model.CompanyUI

@Composable
fun UserCompany(
    company: CompanyUI,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.user_company_text))
        Text(text = stringResource(R.string.user_company_name_text, company.name))
    }
}