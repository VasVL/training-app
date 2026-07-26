package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.UserEntity
import com.vasev.trainingapp.core.database.entity.projection.UserListItemProjection
import kotlinx.coroutines.flow.Flow

/**
 * DAO for the `users` table — basic CRUD + queries / DAO для таблицы `users` — базовые CRUD + запросы
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>): List<Long>

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun observeAll(): Flow<List<UserEntity>>

    @Query("SELECT id, isDefault, name, role FROM users ORDER BY name ASC")
    fun observeForSelection(): Flow<List<UserListItemProjection>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeById(id: Long): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    @Query("SELECT * FROM users WHERE isDefault = 1 LIMIT 1")
    suspend fun getDefault(): UserEntity?

    @Query("SELECT * FROM users WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: String): UserEntity?

    /**
     * Makes exactly one existing user active and leaves users unchanged when the id does not exist /
     * Делает ровно одного существующего пользователя активным и не меняет пользователей, если id не существует
     *
     * One SQL statement prevents an intermediate state with several active users /
     * Один SQL-запрос предотвращает промежуточное состояние с несколькими активными пользователями
     */
    @Query(
        "UPDATE users SET isDefault = CASE WHEN id = :id THEN 1 ELSE 0 END " +
            "WHERE EXISTS (SELECT 1 FROM users WHERE id = :id)",
    )
    suspend fun setActive(id: Long)
}
