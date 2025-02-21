package com.idf.test.data.model

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class AddressDTO(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: GeoDTO
)