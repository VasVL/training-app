package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.UserMaxEntity
import com.vasev.trainingapp.core.database.entity.projection.UserMaxWithExerciseProjection
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `user_maxes` — one-rep-max records of users / DAO для `user_maxes` — записи разовых максимумов пользователей
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface UserMaxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userMax: UserMaxEntity): Long

    @Update
    suspend fun update(userMax: UserMaxEntity)

    @Delete
    suspend fun delete(userMax: UserMaxEntity)

    @Query("SELECT * FROM user_maxes WHERE id = :id")
    suspend fun getById(id: Long): UserMaxEntity?

    @Query("SELECT * FROM user_maxes WHERE userId = :userId ORDER BY measuredAt DESC")
    fun observeByUser(userId: Long): Flow<List<UserMaxEntity>>

    @Query(
        "SELECT user_maxes.exerciseId, exercises.name AS exerciseName, user_maxes.id, " +
            "user_maxes.maxValue, user_maxes.measuredAt, user_maxes.unit FROM user_maxes " +
            "INNER JOIN exercises ON exercises.id = user_maxes.exerciseId " +
            "WHERE user_maxes.userId = :userId ORDER BY user_maxes.measuredAt DESC",
    )
    fun observeForUserProfile(userId: Long): Flow<List<UserMaxWithExerciseProjection>>

    @Query("SELECT * FROM user_maxes WHERE userId = :userId AND exerciseId = :exerciseId ORDER BY measuredAt DESC LIMIT 1")
    suspend fun getLatestForExercise(exerciseId: Long, userId: Long): UserMaxEntity?
}
