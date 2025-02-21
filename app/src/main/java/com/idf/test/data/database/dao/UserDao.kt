package com.idf.test.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.idf.test.data.model.UserDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(users: List<UserDTO>)

    @Query("SELECT * FROM users")
    fun getUserList(): Flow<List<UserDTO>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): Flow<UserDTO?>
}