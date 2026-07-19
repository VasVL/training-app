package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WorkoutLogExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `workout_log_exercises` — exercise entries within workout logs / DAO для `workout_log_exercises` — упражнения в дневнике
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface WorkoutLogExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exercise: WorkoutLogExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(exercises: List<WorkoutLogExerciseEntity>): List<Long>

    @Update
    suspend fun update(exercise: WorkoutLogExerciseEntity)

    @Delete
    suspend fun delete(exercise: WorkoutLogExerciseEntity)

    @Query("SELECT * FROM workout_log_exercises WHERE id = :id")
    suspend fun getById(id: Long): WorkoutLogExerciseEntity?

    @Query("SELECT * FROM workout_log_exercises WHERE workoutLogId = :workoutLogId ORDER BY `order` ASC")
    suspend fun getByWorkoutLog(workoutLogId: Long): List<WorkoutLogExerciseEntity>

    @Query("SELECT * FROM workout_log_exercises WHERE workoutLogId = :workoutLogId ORDER BY `order` ASC")
    fun observeByWorkoutLog(workoutLogId: Long): Flow<List<WorkoutLogExerciseEntity>>
}
