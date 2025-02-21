package com.idf.test.data.repository

import com.idf.test.data.api.IUserApiService
import com.idf.test.data.database.dao.UserDao
import com.idf.test.data.model.UserDTO
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val dao: UserDao,
    private val api: IUserApiService
) : IUserRepository {

    override fun getUserList(): Flow<List<UserDTO>> = dao.getUserList().flowOn(Dispatchers.IO)

    override fun getUser(id: Int): Flow<UserDTO?> = dao.getUser(id).flowOn(Dispatchers.IO)

    override fun refreshUsers(): Flow<RequestStatus> = flow {
        emit(RequestStatus.LOADING)
        try {
            val remoteUserList = api.getUserList()
            dao.insertUserList(remoteUserList)
            emit(RequestStatus.SUCCESSFUL)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(RequestStatus.ERROR)
        }
    }.flowOn(Dispatchers.IO)
}