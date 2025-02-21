package com.idf.test.data.repository

import com.idf.test.data.model.UserDTO
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getUserList(): Flow<List<UserDTO>>
    fun getUser(id: Int): Flow<UserDTO?>
    fun refreshUsers(): Flow<RequestStatus>
}