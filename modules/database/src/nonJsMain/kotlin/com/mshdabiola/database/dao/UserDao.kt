/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun insertUser(user: UserEntity): Long

    @Upsert
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<UserEntity>>

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
