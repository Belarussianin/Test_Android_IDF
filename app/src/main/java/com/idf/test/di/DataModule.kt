package com.idf.test.di

import android.content.Context
import androidx.room.Room
import com.idf.test.data.api.IUserApiService
import com.idf.test.data.database.UserDatabase
import com.idf.test.data.database.dao.UserDao
import com.idf.test.data.repository.IUserRepository
import com.idf.test.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideUserDatabase(
        @ApplicationContext app: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_db_name"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideUserRepository(dao: UserDao, api: IUserApiService): IUserRepository {
        return UserRepository(dao, api)
    }
}