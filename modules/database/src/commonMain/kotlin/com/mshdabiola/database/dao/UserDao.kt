package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao{

    @Upsert
    suspend fun insertUser(user: UserEntity):Long

    @Query("SELECT * FROM user_table")
     fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE id = :id")
     fun getUserById(id: Long): Flow<UserEntity?>

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUser(id: Long)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table WHERE name LIKE :name")
     fun getUserByName(name: String): Flow<UserEntity?>

    @Query("SELECT * FROM user_table WHERE name LIKE :name AND password LIKE :password")
     fun getUserByNameAndPassword(name: String, password: String): Flow<UserEntity?>
}