package com.idf.test.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoUI(
    val lat: String,
    val lng: String
)