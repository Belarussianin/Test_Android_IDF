package com.idf.test.data.model

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDTO(
    @ColumnInfo(name = "company_name") val name: String,
    val catchPhrase: String,
    val bs: String
)