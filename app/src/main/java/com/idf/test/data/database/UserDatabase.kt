package com.idf.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.idf.test.data.database.dao.UserDao
import com.idf.test.data.model.UserDTO

@Database(
    entities = [UserDTO::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}