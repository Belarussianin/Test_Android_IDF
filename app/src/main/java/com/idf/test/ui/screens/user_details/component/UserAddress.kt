package com.idf.test.ui.screens.user_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.idf.test.R
import com.idf.test.ui.model.AddressUI

@Composable
fun UserAddress(
    address: AddressUI,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.user_address_text))
        Text(text = stringResource(R.string.user_address_street_text, address.street))
        Text(text = stringResource(R.string.user_address_suite_text, address.suite))
        Text(text = stringResource(R.string.user_address_city_text, address.city))
        Text(text = stringResource(R.string.user_address_zipcode_text, address.zipcode))
        Text(text = stringResource(R.string.user_geo_text))
        Text(text = stringResource(R.string.user_geo_latitude_text, address.geo.lat))
        Text(text = stringResource(R.string.user_geo_longitude_text, address.geo.lng))
    }
}