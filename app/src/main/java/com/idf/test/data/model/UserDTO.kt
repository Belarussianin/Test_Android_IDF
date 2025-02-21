package com.idf.test.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class UserDTO(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_name") val name: String,
    val username: String,
    val email: String,
    @Embedded val address: AddressDTO,
    val phone: String,
    val website: String,
    @Embedded val company: CompanyDTO
)