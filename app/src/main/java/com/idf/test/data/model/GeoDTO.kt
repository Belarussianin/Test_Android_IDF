package com.idf.test.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoDTO(
    val lat: String,
    val lng: String
)