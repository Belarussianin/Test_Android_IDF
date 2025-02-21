package com.idf.test.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CompanyUI(
    val name: String,
    val catchPhrase: String,
    val bs: String
)