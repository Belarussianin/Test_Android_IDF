package com.idf.test.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUI(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressUI,
    val phone: String,
    val website: String,
    val company: CompanyUI
)