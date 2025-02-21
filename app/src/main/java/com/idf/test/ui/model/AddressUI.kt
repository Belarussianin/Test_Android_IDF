package com.idf.test.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressUI(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoUI
)