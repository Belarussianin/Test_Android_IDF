package com.idf.test.data.api

import com.idf.test.data.model.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApiService(
    private val httpClient: HttpClient
) : IUserApiService {

    override suspend fun getUserList(): List<UserDTO> {
        return httpClient.get(USERS_URL).body<List<UserDTO>>()
    }

    companion object {
        const val USERS_URL = "users"
    }
}