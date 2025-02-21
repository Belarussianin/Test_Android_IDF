package com.idf.test.data.api

import com.idf.test.data.model.UserDTO

interface IUserApiService {
    suspend fun getUserList(): List<UserDTO>
}